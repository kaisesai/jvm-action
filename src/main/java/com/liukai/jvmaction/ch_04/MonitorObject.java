package com.liukai.jvmaction.ch_04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MonitorObject {

  public static void main(String[] args) throws InterruptedException, IOException {

    // 线程死锁演示
    testThreadDeadLock();

    // 监控线程状态
    // testMonitorThread();

    // 内存监控
    // fillHeapTest();
  }

  /**
   * 线程死锁演示
   */
  public static void testThreadDeadLock() {
    for (int i = 0; i < 5; i++) {
      new Thread(new SyncAndRunnable(1, 2)).start();
      new Thread(new SyncAndRunnable(2, 1)).start();
    }
  }

  /**
   * 测试监控线程
   *
   * @throws IOException
   */
  private static void testMonitorThread() throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    br.readLine();
    // 死循环线程
    createBuysThread();
    br.readLine();
    // 等待线程
    createLockThread(new Object());
  }

  /**
   * 线程锁等待演示
   *
   * @param lock
   */
  public static void createLockThread(final Object lock) {
    Thread thread = new Thread(() -> {
      synchronized (lock) {
        try {
          lock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }, "testLockThread");
    thread.start();
  }

  /**
   * 线程死循环演示
   */
  public static void createBuysThread() {
    Thread thread = new Thread(() -> {
      while (true) {
      }
    }, "testBusyThread");
    thread.start();
  }

  private static void fillHeapTest() throws InterruptedException {
    Thread.sleep(5000);
    fillHeap(1000);
    System.gc();
    Thread.sleep(5000);
  }

  /**
   * 4-8 jConsole 监控代码
   * <p>
   * 虚拟机参数：-Xmx100m -Xms100m -XX:+UseSerialGC
   * </p>
   *
   * @param num
   * @throws InterruptedException
   */
  private static void fillHeap(int num) throws InterruptedException {
    List<OOMObject> list = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      // 稍作延迟，令监控曲线变化更加明显
      Thread.sleep(50);
      list.add(new OOMObject());
    }

  }

  /**
   * 4-10 死锁代码检测
   */
  static class SyncAndRunnable implements Runnable {

    int a;

    int b;

    public SyncAndRunnable(int a, int b) {
      this.a = a;
      this.b = b;
    }

    @Override
    public void run() {
      synchronized (Integer.valueOf(a)) {
        synchronized (Integer.valueOf(b)) {
          System.out.println(a + b);
        }
      }
    }

  }

  /**
   * 内存占位符对象：一个 OOMObject 对象大约占 64KB
   */
  static class OOMObject {

    byte[] placeholder = new byte[64 * 1024];

  }

}
