// Test that NTA children can be safely evaluated by multiple threads.
// See bitbucket issue 291 and 292
// https://bitbucket.org/jastadd/jastadd2/issues/291/race-condition-for-grammar-declared-ntas
// https://bitbucket.org/jastadd/jastadd2/issues/292/dynamic-child-vector-is-unsafe-in
// .grammar: { A ::= B*; B ::= <ID> <NUM:Integer> /C/; C ::= <ID>; }
// .options: concurrent rewrite=none
import java.util.concurrent.*;

public class Test {
  static final int N = 100;

  public static void main(String[] args) throws Exception {
    run(1);
    run(4);
  }


  static void run(int numthread) throws Exception {
    CountDownLatch latch = new CountDownLatch(1);
    A a = buildA();

    Worker[] pool = new Worker[numthread];
    for (int i = 0; i < pool.length; ++i) {
      pool[i] = new Worker(latch, a);
      pool[i].start();
    }
    latch.countDown();
    for (Thread t : pool)
      t.join();
    int p = 0;
    for (B b : a.getBList()) {
      C ref = b.getC();
      for (int i = 0; i < pool.length; ++i) {
        if (ref != pool[i].c[p]) {
          System.err.format("Check failed with %d thread%s.%n",
              numthread, numthread == 1 ? "" : "s");
          System.err.format("Index: %d, thread: %d. Expected: %s, was: %s%n",
              p, i, ref, pool[i].c[p]);
          return;
        }
      }
      p += 1;
    }
  }

  static A buildA() {
    A a = new A();
    for (int i = 0; i < N; ++i) {
      a.addB(new B("x" + i + "-", 25));
    }
    return a;
  }
}

class Worker extends Thread {
  final CountDownLatch latch;
  final A a;
  final C[] c;

  Worker(CountDownLatch latch, A a) {
    this.latch = latch;
    this.a = a;
    c = new C[a.getNumB()];
  }

  @Override public void run() {
    try {
      latch.await();
      int i = 0;
      for (B b : a.getBList())
        c[i++] = b.getC();
    } catch (InterruptedException e) {
    }
  }
}
