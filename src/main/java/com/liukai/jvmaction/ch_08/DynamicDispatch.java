package com.liukai.jvmaction.ch_08;

/**
 * 8-8 方法动态分派
 */
public class DynamicDispatch {

  public static void main(String[] args) {
    Human man = new Man();
    Human woman = new Woman();
    man.sayHello();
    woman.sayHello();
    man = new Woman();
    man.sayHello();
  }

  static abstract class Human {

    protected abstract void sayHello();

  }

  static class Man extends Human {

    @Override
    protected void sayHello() {
      System.out.println("Hello Man");
    }

  }

  static class Woman extends Human {

    @Override
    protected void sayHello() {
      System.out.println("Hello Woman");
    }

  }

}
