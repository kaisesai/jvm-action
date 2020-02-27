package com.liukai.jvmaction.ch_03;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * 3-2 引用对象类型
 * <p>
 * VM Args: -Xms10M -Xmx10M -XX:+PrintGCDetails
 * </p>
 */
public class ReferenceObject {

  private static final int _1MB = 1024 * 1024;

  /**
   * 这个成员属性的意义是占点内存，以便能在 GC 日志中看清楚是否回收过
   */
  private byte[] bigSie = new byte[3 * _1MB];

  public static void main(String[] args) {

    // 虚引用
    // phantomReference();

    // 弱引用
    weakReference();

    // 软引用
    // softReference();

    // 强引用
    // strongReference();

  }

  /**
   * 虚引用
   */
  private static void phantomReference() {

    PhantomReference<ReferenceObject> phantomReference = new PhantomReference<>(
      new ReferenceObject(), new ReferenceQueue<>());

    System.out.println("垃圾回收System.gc()之前：phantomReference.get() = " + phantomReference.get());

    // 触发垃圾回收
    System.gc();
    PhantomReference<ReferenceObject> phantomReference1 = new PhantomReference<>(
      new ReferenceObject(), new ReferenceQueue<>());
    System.out.println("垃圾回收System.gc()之后创建新的引用：phantomReference.get() = " + phantomReference.get()
                         + ",\tphantomReference1.get() = " + phantomReference1.get());
  }

  /**
   * 弱引用
   */
  private static void weakReference() {
    WeakReference<ReferenceObject> weakReference = new WeakReference<>(new ReferenceObject());
    System.out.println("垃圾回收System.gc()之前：weakReference.get() = " + weakReference.get());
    // 触发垃圾回收
    System.gc();
    System.out.println("垃圾回收System.gc()之后：weakReference.get() = " + weakReference.get());
    WeakReference<ReferenceObject> weakReference1 = new WeakReference<>(new ReferenceObject());
    System.out.println("垃圾回收System.gc()之后创建新的引用：weakReference.get() = " + weakReference.get()
                         + ",\tweakReference1.get() = " + weakReference1.get());
  }

  /**
   * 软引用
   */
  private static void softReference() {
    SoftReference<ReferenceObject> softReference = new SoftReference<>(new ReferenceObject());
    System.out.println("垃圾回收System.gc()之前：softReference.get() = " + softReference.get());
    // 触发垃圾回收
    System.gc();
    System.out.println("垃圾回收System.gc()之后：softReference.get() = " + softReference.get());
    SoftReference<ReferenceObject> softReference1 = new SoftReference<>(new ReferenceObject());
    System.out.println("垃圾回收System.gc()之后创建新的引用：softReference.get() = " + softReference.get()
                         + ",\tsoftReference1.get() = " + softReference1.get());
  }

  /**
   * 强引用
   */
  private static void strongReference() {
    // 强引用
    ReferenceObject strongReference = new ReferenceObject();
    ReferenceObject strongReference2 = new ReferenceObject();

    // 触发垃圾回收
    System.gc();

  }

}
