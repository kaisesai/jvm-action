package com.liukai.jvmaction.ch_07;

/**
 * 7-1 被动引用的例子之一
 */
public class NotInitialization {

  public static void main(String[] args) {

    //7-1非主动使用类字段演示
    // printSuperClassStaticField();

    // 7-2 通过数组定义来引用类，不会触发此类的初始化
    // newSuperClassArray();

    // 7.3 通过使用常量，不会触发常量所在类的初始化
    // printConstClassConstantValue();

    // 7-6 <clinit>()方法执行顺序
    // printParentStaticField();

    Runnable runnable = () -> {
      System.out.println(Thread.currentThread() + "start");
      DeadLoopClass deadLoopClass = new DeadLoopClass();
      System.out.println(Thread.currentThread() + "run over");
    };

    new Thread(runnable).start();
    new Thread(runnable).start();
  }

  /**
   * 7-6 <clinit>()方法执行顺序
   */
  private static void printParentStaticField() {
    System.out.println(Sub.B);
  }

  /**
   * 7.3 通过使用常量，不会触发常量所在类的初始化
   */
  private static void printConstClassConstantValue() {
    System.out.println(ConstClass.HELLOWORLD);
  }

  /**
   * 7-2 通过数组定义来引用类，不会触发此类的初始化
   */
  private static void newSuperClassArray() {
    SuperrClass[] superrClasses = new SuperrClass[10];
  }

  /**
   * 7-1非主动使用类字段演示
   */
  private static void printSuperClassStaticField() {
    System.out.println(SubClass.value);
  }

  public static class Parent {

    public static int A = 1;

    static {
      A = 2;
    }

  }

  public static class Sub extends Parent {

    public static int B = A;

  }

  /**
   * 被动引用的例子之一：
   * 通过子类引用父类的静态字段，不会导致子类初始化
   */
  public static class SuperrClass {

    public static int value = 123;

    static {
      System.out.println("SuperClass init!");
    }

  }

  public static class SubClass extends SuperrClass {

    static {
      System.out.println("SubClass init!");
    }

  }

  /**
   * 被动使用类字段演示三：
   * 常量在编译阶段会存入调用类的常量池，本质上并没有直接引用到定义常量的类，因此不会触发定义常量类的初始化。
   */
  public static class ConstClass {

    public static final String HELLOWORLD = "hello world";

    static {
      System.out.println("ConstClass init!");
    }

  }

  static class DeadLoopClass {

    static {
      try {
        System.out.println(Thread.currentThread() + "init DeadLoopClass");
        Thread.sleep(1000000L);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
