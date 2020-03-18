package com.liukai.jvmaction.ch_13;

import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 13-2 对 Vector 线程安全的测试
 */
public class VectorThreadSafeTest {

  private static Vector<Integer> vector = new Vector<>();

  public static void main(String[] args) throws InterruptedException {

    ReentrantLock reentrantLock = new ReentrantLock();
    reentrantLock.lock();
    reentrantLock.tryLock();

    while (true) {
      // 为容器添加元素
      for (int i = 0; i < 10; i++) {
        vector.add(i);
      }
      // 不安全的线程操作
      // threadUnsafeOpt();
      // 安全的线程操作
      threadSafeOpt();

      // 不要同时产生过多的线程，否则会导致操作系统假死
      while (Thread.activeCount() > 20) {
        Thread.sleep(1000);
      }

    }

  }

  private static void threadSafeOpt() {
    // 删除容器元素线程
    new Thread(() -> {
      // 进行同步操作
      synchronized (vector) {
        for (int i = 0; i < vector.size(); i++) {
          vector.remove(i);
        }
      }

    }).start();

    // 获取容器元素线程
    new Thread(() -> {
      synchronized (vector) {
        for (int i = 0; i < vector.size(); i++) {
          System.out.println(vector.get(i));
        }
      }
    }).start();
  }

  private static void threadUnsafeOpt() {
    // 删除容器元素线程
    new Thread(() -> {
      for (int i = 0; i < vector.size(); i++) {
        vector.remove(i);
      }
    }).start();

    // 获取容器元素线程
    new Thread(() -> {
      for (int i = 0; i < vector.size(); i++) {
        System.out.println(vector.get(i));
      }
    }).start();
  }

}
