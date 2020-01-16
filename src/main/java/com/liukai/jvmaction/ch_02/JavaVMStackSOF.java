package com.liukai.jvmaction.ch_02;

/**
 * 2-2 虚拟机栈和本地方法栈 OOM 测试
 * <p>
 * VM Args: -Xss160k （设置栈空间最大深度）
 * </p>
 */
public class JavaVMStackSOF {

  private int stackLength = 1;// 栈长度

  public static void main(String[] args) {
    JavaVMStackSOF javaVMStackSOF = new JavaVMStackSOF();
    try {
      javaVMStackSOF.stackLeak();
    } finally {
      System.out.println("stack length:" + javaVMStackSOF.stackLength);
    }
  }

  private void stackLeak() {
    stackLength++;
    stackLeak();
  }

}
