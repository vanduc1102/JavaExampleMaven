package my.example.bruceeckel;

import java.util.function.Function;

public class AreLambdasClosuresSolution {

  class MyInt {
    int i = 0;
  }

  public Function<Integer, Integer> makeFunction() {
    final MyInt n = new MyInt();
    return arg -> {
      System.out.print(n.i + " " + arg + ":");
      arg += 1;
      n.i += arg;
      return n.i;
    };
  }

  public void tryIt() {
    Function<Integer, Integer> x = makeFunction();
    Function<Integer, Integer> y = makeFunction();

    for (int i = 0; i < 5; i++) {
      System.out.println(x.apply(i));
    }

    for (int i = 10; i < 15; i++) {
      System.out.println(y.apply(i));
    }
  }

  public static void main(String[] args) {
    new AreLambdasClosuresSolution().tryIt();
  }
}
