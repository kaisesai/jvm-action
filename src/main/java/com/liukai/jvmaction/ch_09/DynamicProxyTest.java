package com.liukai.jvmaction.ch_09;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 9-1 动态代理的简单示例
 */
public class DynamicProxyTest {

  public static void main(String[] args) {

    System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

    IHello iHello = (IHello) DynamicProxy.createProxyObject(new Hello());
    iHello.sayHello();
  }

  interface IHello {

    void sayHello();

  }

  static class Hello implements IHello {

    @Override
    public void sayHello() {
      System.out.println("hello world");
    }

  }

  static class DynamicProxy implements InvocationHandler {

    private Object target;

    public DynamicProxy(Object target) {
      this.target = target;
    }

    public static Object createProxyObject(Object target) {
      DynamicProxy dynamicProxy = new DynamicProxy(target);
      return Proxy
        .newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
                          dynamicProxy);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      // 注意这里不能输出 proxy 的的相关方法，通过编译出来的 $Proxy0 类，
      // 方法它的相关方法都是调用 代理对象的目标相关方法，在这里调用会造成递归调用该方法，最终导致栈内存溢出
      // System.out.println("welcome" + proxy.toString());
      System.out.println("welcome");
      return method.invoke(target, args);
    }

  }

}
