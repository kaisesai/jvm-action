package com.liukai.jvmaction.ch_10;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementScanner8;
import javax.tools.Diagnostic.Kind;
import java.util.EnumSet;

/**
 * 10-12 命令检查器
 */
public class NameChecker {

  private final Messager messager;

  NameCheckScanner nameCheckScanner = new NameCheckScanner();

  public NameChecker(ProcessingEnvironment processingEnv) {

    this.messager = processingEnv.getMessager();
  }

  /**
   * 对java程序命名进行检查，java名称命名应符合如下格式：
   * <ul>
   * <li>类或接口：符合驼式命名法，首字母大写
   * <li>方法：符合驼式命名法，首字母小写
   * <li>字段：符合驼式命名法，首字母小写
   * <li>类、实例变量：符合驼式命名法，首字母小写
   * <li>常量：要求全部大写
   * </ul>
   */
  public void checkNames(Element element) {
    nameCheckScanner.scan(element);

  }

  /**
   * 名称检查器实现类，继承了jdk1.8中新提供的ElementScanner8<br>
   * 将会以Visitor模式访问抽象语法树中的元素
   */
  private class NameCheckScanner extends ElementScanner8<Void, Void> {

    /**
     * 此方法用于检查java类
     */
    @Override
    public Void visitType(TypeElement e, Void p) {

      scan(e.getTypeParameters(), p);
      checkCamelCase(e, true);
      super.visitType(e, p);
      return null;
    }

    /**
     * 检查方法命名是否合法
     */
    @Override
    public Void visitExecutable(ExecutableElement e, Void p) {

      if (e.getKind() == ElementKind.METHOD) {
        Name name = e.getSimpleName();
        if (name.contentEquals(e.getEnclosingElement().getSimpleName())) {
          messager.printMessage(Kind.WARNING, " 一个普通方法 '" + name + "' 不应该与类名相同，避免与构造方法产生混淆", e);
        }
        checkCamelCase(e, false);
      }

      super.visitExecutable(e, p);
      return null;
    }

    /**
     * 检查变量命名是否合法
     */
    @Override
    public Void visitVariable(VariableElement e, Void p) {

      if (e.getKind() == ElementKind.ENUM_CONSTANT || e.getConstantValue() != null
        || heuristicallyConstant(e)) {
        checkAllCaps(e);
      } else {
        checkCamelCase(e, false);
      }

      return null;
    }

    /**
     * 判断一个变量是否是常量
     *
     * @param e
     * @return
     */
    private boolean heuristicallyConstant(VariableElement e) {

      if (e.getEnclosingElement().getKind() == ElementKind.INTERFACE) {
        return true;
      } else {
        return e.getKind() == ElementKind.FIELD && e.getModifiers()
          .containsAll(EnumSet.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL));
      }
    }

    /**
     * 检查传入的Element是否符合驼式命名法，如果不符合则输出警告信息
     *
     * @param e
     * @param initialCaps
     */
    private void checkCamelCase(Element e, boolean initialCaps) {

      String name = e.getSimpleName().toString();
      boolean previousUpper = false;
      boolean conventional = true;

      int firstCodePoint = name.codePointAt(0);

      if (Character.isUpperCase(firstCodePoint)) {
        previousUpper = true;
        if (!initialCaps) {
          messager.printMessage(Kind.WARNING, "名称  '" + name + " ' 应当以小写字母开头", e);
          return;
        }
      } else if (Character.isLowerCase(firstCodePoint)) {
        if (initialCaps) {
          messager.printMessage(Kind.WARNING, "名称  '" + name + " ' 应当以大写字母开头", e);
          return;
        }
      } else {
        conventional = false;
      }

      if (conventional) {
        int cp = firstCodePoint;
        for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
          cp = name.codePointAt(i);
          if (Character.isUpperCase(cp)) {
            if (previousUpper) {
              conventional = false;
              break;
            }
            previousUpper = true;
          } else {
            previousUpper = false;
          }
        }

      }
      if (!conventional) {
        messager.printMessage(Kind.WARNING, "名称  '" + name + " ' 应当符合驼式命名法", e);
      }

    }

    /**
     * 大写命名检查，要求第一个字母是大写的英文字母，其余部分是大写字母或下划线
     *
     * @param e
     */
    private void checkAllCaps(VariableElement e) {

      String name = e.getSimpleName().toString();
      boolean conventional = true;

      int firstCodePoint = name.codePointAt(0);

      if (!Character.isUpperCase(firstCodePoint)) {
        conventional = false;
      } else {
        boolean previousUnderscore = false;

        int cp = firstCodePoint;
        for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
          cp = name.codePointAt(i);
          if (cp == '_') {
            if (previousUnderscore) {
              conventional = false;
              break;
            }
            previousUnderscore = true;
          } else {
            previousUnderscore = false;
            if (!Character.isUpperCase(cp) && !Character.isDigit(cp)) {
              conventional = false;
              break;
            }
          }
        }
      }
      if (!conventional) {
        messager.printMessage(Kind.WARNING, "常量  '" + name + " ' 应当全部以大写字母或下划线命名，并且以字母开头", e);
      }
    }

  }

}
