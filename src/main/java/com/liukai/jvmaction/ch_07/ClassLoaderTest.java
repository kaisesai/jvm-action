package com.liukai.jvmaction.ch_07;

import java.io.IOException;
import java.io.InputStream;

/**
 * 7-8 不同类加载器对 instanceof 关键字运算结果的影响
 */
public class ClassLoaderTest {

  public static void main(String[] args)
    throws ClassNotFoundException, IllegalAccessException, InstantiationException {

    // 创建自定义的类加载器
    ClassLoader classLoader = new ClassLoader() {
      @Override
      public Class<?> loadClass(String name) throws ClassNotFoundException {

        try {
          // 类 Class 的简写名称
          String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";

          // 通过类加载class 路径下的类二进制字节流
          InputStream is = this.getClass().getResourceAsStream(fileName);
          //无法读取二进制字节流则使用父类加载方法
          if (is == null) {
            return super.loadClass(name);
          }

          byte[] b = new byte[is.available()];
          is.read(b);
          return defineClass(name, b, 0, b.length);
        } catch (IOException e) {
          throw new ClassNotFoundException(name);
        }

      }
    };

    // 通过自定义的类加载器加载该类并创建实例，并使用 instanceof 操作符判断该类类型
    Object o = classLoader.loadClass("com.liukai.jvmaction.ch_07.ClassLoaderTest").newInstance();
    System.out.println(o.getClass());
    System.out.println(o instanceof ClassLoaderTest);

  }

}
