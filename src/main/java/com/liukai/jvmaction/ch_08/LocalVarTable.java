package com.liukai.jvmaction.ch_08;

/**
 * 8-1 局部变量表
 */
public class LocalVarTable {

  public static void main(String[] args) {
    // 影响回收方法 1
    // placeholderGc1();

    // 影响回收方法 2
    // placeholderGc2();

    // 影响回收方法 3
    placeholderGc3();
  }

  /**
   * 这样才能回收
   */
  private static void placeholderGc3() {
    {
      byte[] placeholder = new byte[64 * 1024 * 1024];
    }
    int a = 0;
    System.gc();
  }

  /**
   * 不能回收 placeholder 占用的内存
   */
  private static void placeholderGc2() {
    {
      byte[] placeholder = new byte[64 * 1024 * 1024];
    }
    System.gc();
  }

  /**
   * 不能回收 placeholder 占用的内存
   */
  private static void placeholderGc1() {
    byte[] placeholder = new byte[64 * 1024 * 1024];
    System.gc();
  }

}
