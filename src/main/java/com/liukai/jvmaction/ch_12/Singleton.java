package com.liukai.jvmaction.ch_12;

/**
 * 12-5 DCL 单例模式<br/>
 * <p>
 * 虚拟机参数：-Xcomp -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly -XX:CompileCommand=print,*Singleton.getInstance -XX:CompileCommand=dontinline,*Singleton.getInstance -XX:CompileCommand=compileonly,*Singleton.getInstance <br/></>
 * <p>
 * 描述：
 * -Xcomp 启用编译模式，不用默认的编译阈值即可进行汇编（方法调用计数器阈值与回边计数器阈值参见上篇博客：10_晚期优惠)） <br>
 * -XX:+UnlockDiagnosticVMOptions 解锁虚拟机调试设置
 * -XX:+PrintAssembly 输出编译的机器代码信息
 * -XX:CompileCommand=print,*Singleton.getInstance 编译命令选项：只输出 getInstance 方法
 * -XX:CompileCommand=dontinline,*Singleton.getInstance  编译命令选项：getInstance 方法不进行方法内联
 * -XX:CompileCommand=compileonly,*Singleton.getInstance 编译命令选项：只编译 getInstance 方法
 * <br>
 * 注意，如果我们只是用 PrintAssembly 参数，会输出所有机器代码的反汇编信息，<br>
 * 有些情况下我们只想输出特定的目标方法的反汇编信息，这时候可以使用虚拟机参数 CompileCommand 过滤出我们想要的方法输出反汇编信息。
 * （比如我们想只想打印某个方法，需要我们通加上 -XX:CompileCommand=compileonly,*Singleton.getInstance，）
 * 当然也可以只启用 CompileCommand 命令过滤与输出，不用额外携带 PrintAssembly 参数，
 * （比如执行-XX:CompileCommand=print,*Singleton.getInstance -XX:CompileCommand=compileonly,*Singleton.getInstance）
 * <p>
 * 虚拟机参数CompileCommand 详情：https://blog.csdn.net/ning0323/article/details/75451955
 */
public class Singleton {

  private static volatile Singleton singleton;

  public static Singleton getInstance() {
    if (singleton == null) {
      synchronized (Singleton.class) {
        if (singleton == null) {
          singleton = new Singleton();
        }
      }
    }
    return singleton;
  }

  public static void main(String[] args) throws InterruptedException {
    Singleton.getInstance();
  }

}
