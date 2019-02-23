package my.example.bruceeckel;

import java.util.function.Function;

public class AreLambdasClosures {

  public Function<Integer, Integer> makeFunction() {
    int n = 0;
    return arg -> {
      System.out.print(n + " " + arg + ":");
      arg += 1;
      // n +=1;
      // Variable used in lambda expression should be final or effectively final
      return n + arg;
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
    new AreLambdasClosures().tryIt();
  }
}
