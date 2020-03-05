package com.liukai.jvmaction.ch_08;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * 8-11 MethodHandle 演示
 */
public class MethodHandleTest {

  public static void main(String[] args) throws Throwable {
    Object o = new ClassA();
    getPrintlnMH(o).invokeExact("hello liukai");
  }

  public static MethodHandle getPrintlnMH(Object reveiver)
    throws NoSuchMethodException, IllegalAccessException {
    // MethodType: 代表“方法类型”，包含了方法的返回值和具体参数
    MethodType methodType = MethodType.methodType(void.class, String.class);
    // lookup方法是在指定类中查找符合给定的方法名称、方法类型，并且符合调用权限的方法句柄
    // findVirtual 是调用一个虚方法，同时绑定方法的接收者
    return MethodHandles.lookup().findVirtual(reveiver.getClass(), "println", methodType)
      .bindTo(reveiver);
  }

  static class ClassA {

    public void println(String s) {
      System.out.println(s);
    }

  }

}
