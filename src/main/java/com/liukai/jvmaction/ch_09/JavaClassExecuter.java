package com.liukai.jvmaction.ch_09;

import java.lang.reflect.Method;

/**
 * JavaClass执行工具
 */
public class JavaClassExecuter {

  /**
   * 执行外部传过来的代表一个Java类的Byte数组<br>
   * 将输入类的byte数组中代表java.lang.System的CONSTANT_Utf8_info常量修改为劫持后的HackSystem类
   * 执行方法为该类的static main(String[] args)方法，输出结果为该类向System.out/err输出的信息
   *
   * @param classByte 代表一个Java类的Byte数组
   * @return 执行结果
   */
  public static String execute(byte[] classByte) {
    // 清理出自定义类的输出流缓冲
    HackSystem.clearBuffer();
    // 修改字节码中的标准输出为自定义输出类的符号引用
    ClassModifier cm = new ClassModifier(classByte);
    byte[] modiBytes = cm
      .modifyUTF8Constant("java/lang/System", "com/liukai/jvmaction/ch_09/HackSystem");
    // 创建自定义加载类加载字节码为 Class 对象
    HotSwapClassLoader loader = new HotSwapClassLoader();
    Class clazz = loader.loadByte(modiBytes);
    try {
      // 通过反射执行字节码类的 main 方法
      Method method = clazz.getMethod("main", String[].class);
      method.invoke(null, new String[] {null});
    } catch (Throwable e) {
      // 将异常信息输出到自定义输出流中
      e.printStackTrace(HackSystem.out);
    }
    // 返回自定义输出流的信息
    return HackSystem.getBufferString();
  }

}
