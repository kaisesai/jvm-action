package com.liukai.jvmaction.ch_03;

/**
 * 3-2 一次对象自我拯救的演示
 * <p>
 * 1. 对象可以在 GC 时自我拯救
 * 2. 这种自救机会只有一次，因为一个对象的 finaize() 方法最多只会被系统自动调用一次
 * </p>
 */
public class FinalizeEscapeGC {

  public static FinalizeEscapeGC SAVE_HOOK = null;

  public static void main(String[] args) throws InterruptedException {
    SAVE_HOOK = new FinalizeEscapeGC();

    // 对象第一次自我拯救
    ownSelfSave();

    // 再来一次自我拯救——失败了
    ownSelfSave();

  }

  /**
   * 自我拯救
   *
   * @throws InterruptedException
   */
  private static void ownSelfSave() throws InterruptedException {
    SAVE_HOOK = null;
    System.gc();
    // 因为 finalize 方法优先级很低，所以暂停 0.5 秒等待它
    Thread.sleep(500);

    if (SAVE_HOOK != null) {
      SAVE_HOOK.isAlive();
    } else {
      System.out.println("no, i am dead :(");
    }
  }

  public void isAlive() {
    System.out.println("yes, i am still alive :)");
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    System.out.println("finalize method executed!");
    FinalizeEscapeGC.SAVE_HOOK = this;
  }

}
