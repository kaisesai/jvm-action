package com.liukai.jvmaction.ch_02;

import java.util.ArrayList;
import java.util.List;

/**
 * 2-1 堆内存异常
 * <p>
 * VM args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * </p>
 */
public class HeapOOM {

  public static void main(String[] args) {
    List<OOMObject> list = new ArrayList<>();

    while (true) {
      list.add(new OOMObject());
    }

  }

}
