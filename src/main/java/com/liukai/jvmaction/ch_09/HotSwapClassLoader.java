package com.liukai.jvmaction.ch_09;

/**
 * 9-3 热插拔类加载器
 * <p>
 * 为了多次载入执行类而加入的类加载器。
 * 把 defineClass 方法开发出来，只有外部显示调用时才会使用到 loadByte 方法。
 * 有虚拟机调用时，任然按照原先的双亲委派模型则使用 loadClass方法进行加载。
 * </p>
 */
public class HotSwapClassLoader extends ClassLoader {

  public HotSwapClassLoader() {
    // 这里实际使用的父类是web 服务器的加载器，我这边用的是 tomcat，它的加载器是 ParallelWebappClassLoader
    super(HotSwapClassLoader.class.getClassLoader());
  }

  public Class loadByte(byte[] classByte) {
    return defineClass(null, classByte, 0, classByte.length);
  }

}
