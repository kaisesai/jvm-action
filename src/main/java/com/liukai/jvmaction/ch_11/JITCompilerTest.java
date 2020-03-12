package com.liukai.jvmaction.ch_11;

/**
 * 11-2 即时编译器测试代码<br/>
 * 虚拟机参数：-XX:+PrintCompilation -XX:+PrintInlining -XX:+UnlockDiagnosticVMOptions
 * <br/>
 * -XX:+PrintCompilation 参数表示输出即时编译器编译的信息<br/>
 * -XX:+PrintInlining 参数表示输出方法内联信息,<br/>
 * -XX:+PrintAssembly 参数表示把动态生成的本地代码还原成为汇编代码输出，<br/>
 * -XX:+PrintOptoAssembly 参数表示也是同上参数，只不过会带有注释，<br/>
 * -XX:+PrintIdealGraphFile 参数零虚拟机将编译过程中各个阶段数据输出到文件<br/>
 * 注意我使用的虚拟机时 product版本的，使用上述部分参数需要加额外的参数才能使用，参数 -XX:+UnlockDiagnosticVMOptions 开启解锁虚拟机诊断参数配置<br/>
 * -XX:+TraceClassLoading 输出类加载信息<br/>
 * -XX:+LogCompilation 将虚拟机信息输出到文件<br/>
 */
public class JITCompilerTest {

  public static final int NUM = 15000;

  public static int doubleValue(int i) {
    // 空循环用于后面演示 JIT 代码优化过程
    for (int j = 0; j < 10000; j++) {
    }
    return i * 2;
  }

  public static long calSum() {
    long sum = 0;
    for (int i = 0; i < 100; i++) {
      sum += doubleValue(i);
    }
    return sum;
  }

  public static void main(String[] args) {
    for (int i = 0; i < NUM; i++) {
      calSum();
    }
  }

}
