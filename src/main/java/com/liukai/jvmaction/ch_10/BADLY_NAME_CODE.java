package com.liukai.jvmaction.ch_10;

/**
 * 10-13 包含多个不规范命名的代码样例
 */
public class BADLY_NAME_CODE {

  static final int _FORTY_TWO = 42;

  public static int NOT_A_CONSTANT = _FORTY_TWO;

  protected void BADLY_NAMED_CODE() {
    return;
  }

  public void NOTCASEmethodNAME() {
    return;
  }

  enum colors {
    red, blue, green
  }

}
