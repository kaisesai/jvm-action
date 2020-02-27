package com.liukai.jvmaction.ch_03;

/**
 * 3-1 引用计数算法的缺陷
 * <p>
 * VM Args: -Xms10M -Xmx10M -XX:+PrintGCDetails
 * </p>
 */
public class ReferenceCountingGC {

  private static final int _1MB = 1024 * 1024;

  public Object instance = null;

  /**
   * 这个成员属性的意义是占点内存，以便能在 GC 日志中看清楚是否回收过
   */
  private byte[] bigSie = new byte[2 * _1MB];

  public static void main(String[] args) {

    ReferenceCountingGC objA = new ReferenceCountingGC();
    ReferenceCountingGC objB = new ReferenceCountingGC();

    objA.instance = objB;
    objB.instance = objA;

    objA = null;
    objB = null;

    // 假设这里发生 GC，objA 和 objB 是否能被回收
    System.gc();

  }

}
