package com.liukai.jvmaction.ch_03;

/**
 * 3-5 分配对象
 */
public class AllocationObject {

  private static final int _1MB = 1024 * 1024;

  public static void main(String[] args) {

    // 长期存活的对象将进入老年代
    testHandlePromotion();

    // 动态判定对象年龄
    // testTenuringThreshold2();

    // 长期存活的对象将进入老年代
    // testTenuringThreshold();

    // 大对象直接进入老年代
    // tetPretenureSizeThreshold();
    // 新生代 minor gc
    // testAllocation();
  }

  /**
   * 3-9 长期存活的对象将进入老年代
   * <p>
   * JVM Args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+PrintCommandLineFlags -XX:+UseParNewGC
   * -XX:MaxTenuringThreshold=1
   * </p>
   */
  public static void testHandlePromotion() {
    byte[] allocation1, allocation2, allocation3, allocation4, allocation5, allocation6,
      allocation7;
    allocation1 = new byte[2 * _1MB];
    allocation2 = new byte[2 * _1MB];
    allocation3 = new byte[2 * _1MB];

    allocation1 = null;

    allocation4 = new byte[2 * _1MB];
    allocation5 = new byte[2 * _1MB];
    allocation6 = new byte[2 * _1MB];

    allocation4 = null;
    allocation5 = null;
    allocation6 = null;

    allocation7 = new byte[2 * _1MB];

  }

  /**
   * 3-8 动态对象年龄判定
   * <p>
   * JVM Args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+PrintCommandLineFlags -XX:+UseParNewGC
   * * -XX:MaxTenuringThreshold=15
   * </p>
   */
  public static void testTenuringThreshold2() {
    byte[] allocation1, allocation2, allocation3, allocation4;
    allocation1 = new byte[_1MB / 4];
    allocation2 = new byte[_1MB / 4];
    // allocation1 + allocation2 大于 survivor空间的一半值
    // allocation3 = new byte[4 * _1MB];
    allocation4 = new byte[4 * _1MB];

    allocation4 = null;
    allocation4 = new byte[4 * _1MB];

  }

  /**
   * 3-7 长期存活的对象将进入老年代
   * <p>
   * JVM Args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+PrintCommandLineFlags -XX:+UseParNewGC
   * -XX:MaxTenuringThreshold=1
   * </p>
   */
  public static void testTenuringThreshold() {
    byte[] allocation1, allocation2, allocation3;
    allocation1 = new byte[_1MB / 4];
    allocation2 = new byte[4 * _1MB];
    // 发生一次 minor gc，allocation1 进入了survivor 区域
    System.out.println("即将发生第1次 gc");
    allocation3 = new byte[4 * _1MB];
    allocation3 = null;
    // 发生一次 minor gc
    System.out.println("即将发生第2次 gc");
    allocation3 = new byte[4 * _1MB];
    System.out.println(1);
  }

  /**
   * 3-6 大对象直接进入老年代
   * <p>
   * JVM Args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+PrintCommandLineFlags -XX:+UseParNewGC -XX:PretenureSizeThreshold=3145728
   * </p>
   */
  public static void tetPretenureSizeThreshold() {
    byte[] allocation = new byte[4 * _1MB];
  }

  /**
   * 3-5 新生代 minor gc
   * <p>
   * JVM Args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+PrintCommandLineFlags -XX:+UseParNewGC
   * </p>
   */
  public static void testAllocation() {
    byte[] allocation1 = new byte[2 * _1MB];
    byte[] allocation2 = new byte[2 * _1MB];
    byte[] allocation3 = new byte[2 * _1MB];
    byte[] allocation4 = new byte[4 * _1MB];// 发生了一次 minor gc
  }

}
