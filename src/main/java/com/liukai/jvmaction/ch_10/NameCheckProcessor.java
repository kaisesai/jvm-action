package com.liukai.jvmaction.ch_10;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * 10-11 注解处理器 NameCheckProcessor
 */
//可以用"*"表示支持所有Annotations，可以使用通配符，如果需要不同包下的话可以使用{}进行分割
@SupportedAnnotationTypes("*")
//这里填写支持的java版本
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class NameCheckProcessor extends AbstractProcessor {

  private NameChecker nameChecker;

  /**
   * 初始化名称检查插件,processingEnv为注解处理器提供的上下文环境
   */
  @Override
  public void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    nameChecker = new NameChecker(processingEnv);
  }

  /**
   * 对输入的语法树的各个节点进行进行名称检查
   */
  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    if (!roundEnv.processingOver()) {
      for (Element element : roundEnv.getRootElements())
      //这里可以执行想要的操作
      {
        nameChecker.checkNames(element);
      }
    }
    return false;
  }

}
