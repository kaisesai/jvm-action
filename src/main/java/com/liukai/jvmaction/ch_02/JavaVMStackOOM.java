package com.liukai.jvmaction.ch_02;

/**
 * 2-3 创建线程导致内存溢出异常
 * <p>
 * VM Args: -Xss2M
 * </p>
 */
public class JavaVMStackOOM {

  public static void main(String[] args) {
    JavaVMStackOOM javaVMStackOOM = new JavaVMStackOOM();
    javaVMStackOOM.stackLeakByThread();
  }

  private void dontStop() {
    // 让线程一直 sleep
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    // while (true) {
    // 确保线程一直存活，可能会使系统假死，这种方式没有让线程持续 sleep 要好
    // }
  }

  public void stackLeakByThread() {
    int i = 0;
    try {
      while (true) {
        i++;
        new Thread(JavaVMStackOOM.this::dontStop).start();
      }
    } finally {
      System.out.println("OutOfMemoryError: max thread num:" + i);
    }
  }

}
