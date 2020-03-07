package com.liukai.jvmaction.ch_09;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * 为JavaClass劫持java.lang.System提供支持
 * 除了out和err外，其余的都直接转发给System处理
 */
public class HackSystem {

  private static final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

  public static final PrintStream out = new PrintStream(buffer);

  public static PrintStream err = out;

  public static String getBufferString() {
    return buffer.toString();
  }

  public static void clearBuffer() {
    buffer.reset();
  }
  // 下面所有的方法都与java.lang.System的名称一样
  // 实现都是字节转调System的对应方法
  // 因版面原因，省略了其他方法
}
