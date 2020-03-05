package com.liukai.jvmaction.ch_08;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * 8-15 使用 MethodHandle  解决问题
 */
public class MethodHandleDynamicDispatch {

  public static void main(String[] args) {
    new Son().thinking();

    int a = 5;
    int b = 127;
    int c = a + b;
  }

  static class GrandFather {

    void thinking() {
      System.out.println("i am grandfather");
    }

  }

  static class Father extends GrandFather {

    void thinking() {
      System.out.println("i am father");
    }

  }

  static class Son extends Father {

    void thinking() {
      // 这里实现调用父类 GrandFather 的 thinking 方法
      try {
        MethodType mt = MethodType.methodType(void.class);
        // 通过使用 findSpecial 方法，对应 invokespecial 指令，它可以执行父类方法。
        MethodHandle mh = MethodHandles.lookup()
          .findSpecial(GrandFather.class, "thinking", mt, this.getClass());
        mh.invoke(this);
      } catch (Throwable e) {
        e.printStackTrace();
      }

    }

  }

}
