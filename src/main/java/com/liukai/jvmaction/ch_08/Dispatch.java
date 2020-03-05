package com.liukai.jvmaction.ch_08;

/**
 * 8-10 单分派与多分派
 */
public class Dispatch {

  public static void main(String[] args) {
    Father father = new Father();
    Father son = new Son();
    father.hardChoice(new QQ());
    son.hardChoice(new _360());
  }

  static class QQ {

  }

  static class _360 {

  }

  static class Father {

    public void hardChoice(QQ args) {
      System.out.println("father choice qq");
    }

    public void hardChoice(_360 args) {
      System.out.println("father choice 360");
    }

  }

  static class Son extends Father {

    @Override
    public void hardChoice(QQ args) {
      System.out.println("son choice qq");
    }

    @Override
    public void hardChoice(_360 args) {
      System.out.println("son choice 360");
    }

  }

}
