package com.liukai.jvmaction.ch_02;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * 2-5 借助 CGLib 使的方法区出现内存溢出异常
 * <p>
 * VM Args: -XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10M
 * </p>
 */
public class JavaMethodAreOOM {

  public static void main(String[] args) {
    try {
      while (true) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(OOMObject.class);
        enhancer.setUseCache(false);
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> methodProxy
          .invokeSuper(o, objects));
        enhancer.create();
      }
    } catch (Throwable e) {
      System.out.println(e.toString());
      throw e;
    }
  }

}
