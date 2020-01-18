package com.liukai.jvmaction.ch_02;

import java.util.ArrayList;
import java.util.List;

/**
 * 2-4 运行时常量池导致的内存溢出异常
 * <p>
 * VM Args: -XX:PermSize=10M -XX:MaxPermSize=10M （JDK1.7 中 ）
 * -Xms10M -Xmx10M（JDK 8 运行时常量池放在堆区中 ）
 * </p>
 */
public class RuntimeConstantPoolOOM {

  public static void main(String[] args) {
    // 使用 list 保持着常量池引用，避免 Full GC 回收常量池行为
    // runtimeConstantPoolOOM();

    // 测试字符串的 intern 方法在常量池的表现
    compareStringInternForConstantPool();

  }

  private static void compareStringInternForConstantPool() {
    String str1 = new StringBuilder().append("计算机").append("软件").toString();
    System.out.println(str1.intern() == str1);

    String str2 = new StringBuilder().append("ja").append("va").toString();
    System.out.println(str2.intern() == str2);
  }

  private static void runtimeConstantPoolOOM() {
    List<String> list = new ArrayList<>();
    int i = 0;
    while (true) {
      // 10M 的 PermSize 在 Integer 范围内足够产生 OOM
      i++;
      list.add(String.valueOf(i).intern());
    }
  }

}
