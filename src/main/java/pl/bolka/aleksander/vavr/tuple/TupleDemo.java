package pl.bolka.aleksander.vavr.tuple;

import io.vavr.Tuple;
import io.vavr.Tuple1;
import io.vavr.Tuple3;

public class TupleDemo {

  public static void main(String[] args) {
    Tuple1<String> value = Tuple.of("Value");
    System.out.println(value._1);

    Tuple3<String, Two, Integer> numbers = Tuple.of("raz", new Two(), 3);
    System.out.println(numbers._1);
    System.out.println(numbers._2);
    System.out.println(numbers._3);

    Tuple3<String, String, Integer> mappedTuple = numbers.map(
        s -> s + "!",
        two -> two.toString(),
        integer -> integer * 2);
    System.out.println(mappedTuple);

    Tuple3<String, Integer, String> some = mappedTuple.map((s, s2, integer) -> Tuple.of(s + s2 + integer, 2, "Some"));
    System.out.println(some);

    String apply = mappedTuple.apply((s, s2, integer) -> s + s2 + integer);
    System.out.println(apply);

  }

  static class Two {
    public static final int value = 2;
  }

}
