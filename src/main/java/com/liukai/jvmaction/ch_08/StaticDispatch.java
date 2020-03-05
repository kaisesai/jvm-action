package com.liukai.jvmaction.ch_08;

/**
 * 8-6 方法静态分派
 */
public class StaticDispatch {

  public static void main(String[] args) {
    Human man = new Man();
    Human woman = new Woman();
    StaticDispatch sr = new StaticDispatch();
    sr.sayHello(man);
    sr.sayHello(woman);
  }

  public void sayHello(Human human) {
    System.out.println("Hello human!");
  }

  public void sayHello(Man man) {
    System.out.println("Hello man!");
  }

  public void sayHello(Woman woman) {
    System.out.println("Hello woman!");
  }

  static abstract class Human {

  }

  static class Man extends Human {

  }

  static class Woman extends Human {

  }

}
