package com.liukai.jvmaction.ch_08;

/**
 * 8-5 方法静态解析
 */
public class StaticResolution {

  public static void main(String[] args) {
    StaticResolution.sayHello();
  }

  public static void sayHello() {
    System.out.println("Hello!");
  }

}
