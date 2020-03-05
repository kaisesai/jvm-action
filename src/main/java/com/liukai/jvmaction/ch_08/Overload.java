package com.liukai.jvmaction.ch_08;

import java.io.Serializable;

/**
 * 8-7 重载方法优先级
 */
public class Overload {

  public static void main(String[] args) {
    sayHello('a');
  }

  // 优先级：1
  public static void sayHello(char c) {
    System.out.println("Hello char");
  }

  // 优先级：2
  public static void sayHello(int i) {
    System.out.println("Hello int");
  }

  // 优先级：3
  public static void sayHello(long l) {
    System.out.println("Hello long");
  }

  // 优先级：4
  public static void sayHello(Character c) {
    System.out.println("Hello Character");
  }

  // 优先级：5
  public static void sayHello(Serializable s) {
    System.out.println("Hello Serializable");
  }

  // 优先级：6
  public static void sayHello(Object o) {
    System.out.println("Hello Object");
  }

  // 编译失败，错误: 不兼容的类型: char无法转换为int[]
  public static void sayHello(char[] chars) {
    System.out.println("Hello char[]");
  }

}
