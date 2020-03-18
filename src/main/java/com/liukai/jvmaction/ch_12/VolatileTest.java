package com.liukai.jvmaction.ch_12;

/**
 * 12-1 volatile 运算
 */
public class VolatileTest {

  private static final int THREADS_COUNT = 20;

  public static volatile int race = 0;

  public static void increase() {
    race++;
  }

  public static void main(String[] args) {
    for (int i = 0; i < THREADS_COUNT; i++) {
      new Thread(() -> {

        for (int j = 0; j < 10000; j++) {
          increase();
        }
      }, "线程" + i).start();
    }

    // 等待所有累加线程都结束
    while (Thread.activeCount() > 1) {
      Thread.yield();
    }
    System.out.println(race);
  }

}
