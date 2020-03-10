package com.liukai.jvmaction.ch_10;

import java.util.List;

public class Test10 {

  public static void main(String[] args) {
    box();
  }

  /**
   * 10-9 条件编译
   */
  public static void conditionCompiler() {
    if (true) {
      // 编译成字节码文件后，只会有下面这行代码的字节码指令
      System.out.println(1);
    } else {
      System.out.println(2);
    }
  }

  /**
   * 10-8 自动装箱的陷阱
   */
  private static void box() {
    Integer a = 1;
    Integer b = 2;
    Integer c = 3;
    Integer d = 3;
    Integer e = 321;
    Integer f = 321;
    Long g = 3L;

    System.out.println(c == d);// true
    System.out.println(e == f);// false
    System.out.println(c == (a + b));// true
    System.out.println(c.equals(a + b));//true
    // 包装类的“==”运算在不遇到算术运算的情况下不会自动拆箱，以及它们的 equals(0 方法不处理数据转型的关心
    System.out.println(g == (a + b));// true
    System.out.println(g.equals(a + b));// false
  }

  /**
   * 10-4 当泛型遇到重载
   */
  public static void method() {
    System.out.println("m0");
  }

  public static void method(List<Integer> args) {
    System.out.println("m1");
  }

}
