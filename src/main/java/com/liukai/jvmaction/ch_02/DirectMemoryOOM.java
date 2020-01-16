package com.liukai.jvmaction.ch_02;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * 2-6 使用 unsafe 分配本机内存
 * <p>
 * VM Args: -Xmx20M -XX:MaxDirectMemorySize=10M
 * </p>
 */
public class DirectMemoryOOM {

  private static final int _1MB = 1024 * 1024 * 1024;

  public static void main(String[] args) throws IllegalAccessException {
    // Unsafe 类中声明的第一个字段就是 private static final Unsafe theUnsafe;
    directMemoryOOMFromUnsafe();

    // 使用 DirectByteBuffer 使直接内存溢出
    // directMemoryOOMFromDirectByteBuffer();
  }

  /**
   * DirectByteBuffer 直接内存溢出
   */
  private static void directMemoryOOMFromDirectByteBuffer() {
    List<ByteBuffer> buffers = new ArrayList<>();
    int count = 1;
    try {
      while (true) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(_1MB);
        buffers.add(byteBuffer);
        count++;
      }
    } finally {
      System.out.println("直接分配内存次数：" + count);
    }
  }

  /**
   * 使用 Unsafe 方法直接分配内存
   *
   * @throws IllegalAccessException
   */
  private static void directMemoryOOMFromUnsafe() throws IllegalAccessException {
    Field unsafeField = Unsafe.class.getDeclaredFields()[0];
    unsafeField.setAccessible(true);// 设置访问权限
    Unsafe unsafe = (Unsafe) unsafeField.get(null);//获取字段实例值
    while (true) {
      // 分配内存
      unsafe.allocateMemory(_1MB);
    }
  }

}
