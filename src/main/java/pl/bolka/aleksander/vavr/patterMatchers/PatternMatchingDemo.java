package pl.bolka.aleksander.vavr.patterMatchers;

import static io.vavr.API.*;
import static io.vavr.Patterns.*;
import static io.vavr.Predicates.*;
import static io.vavr.Predicates.allOf;

import io.vavr.control.Option;
import io.vavr.control.Try;
import java.util.Objects;
import pl.bolka.aleksander.vavr.option.task.User;

public class PatternMatchingDemo {

  public static void main(String[] args) {
    baseNumberMatches();

    predicates();

    patterns();

    moreComplexExamples();
  }

  private static void baseNumberMatches() {
    int i = 0;

    String result = Match(i).of(
        Case($(1), "one"),
        Case($(2), "two"),
        Case($(), "?")
    );

    System.out.println(result);

    Option<String> optionResult = Match(i).option(
        Case($(0), "zero")
    );

    System.out.println(optionResult);
  }

  private static void predicates() {
    User user = User.builder()
                    .id(12)
                    .name("Jan")
                    .build();

    Boolean result = Match(user).of(
        Case($(is(new User())), false),
        Case($(is(User.builder().id(12).build())), true),
        Case($(), false)
    );

    System.out.println(result);

    Integer intResult = Match(user.getName()).of(
        Case($(isIn("Franek", "Janek")), 1),
        Case($(isIn("Halina", "Grażyna")), 2),
        Case($(isIn("Jan", "Filip")), 3)
    );

    System.out.println(intResult);

    Match(user.getName()).of(
        Case($(isIn("Franek", "Janek")), name -> run(() -> System.out.println("Franek lub Janek"))),
        Case($(isIn("Halina", "Grażyna")), name -> run(() -> System.out.println(name.toUpperCase()))),
        Case($(isIn("Jan", "Filip")), name -> run(() -> {
          System.out.println("Bardzo");
          System.out.println(" długa");
          System.out.println(" metoda");
        }))
    );

    Integer someInt = 1;
    Object objectInt = someInt;

    Number plusOne = Match(objectInt).of(
        Case($(instanceOf(Integer.class)), i -> i + 1),
        Case($(instanceOf(Double.class)), d -> d + 2.0d),
        Case($(), o -> {
          throw new NumberFormatException();
        })
    );

    System.out.println(plusOne);
  }


  private static void patterns() {
    Try<Void> someTry = Try.run(() -> exceptionMethod());

    String of = Match(someTry).of(
        Case($Success($()), value -> "Działa!"),
        Case($Failure($()), x -> "No i sie wywaliło ;/")
    );

    System.out.println(of);

    Option<Object> option = Option.of(null);

    String optionDemo = Match(option).of(
        Case($Some($()), "defined"),
        Case($None(), "empty")
    );

    System.out.println(optionDemo);
  }

  private static void moreComplexExamples() {
    String anyOf = Match(1).of(
        Case($(anyOf(is(1), is(2))), tuple2 -> "To jest 1 albo 2"),
        Case($(anyOf(is(10), is(312))), tuple2 -> "To jest 10  albo 312")
    );

    System.out.println(anyOf);

    String allOf = Match(1).of(
        Case($(allOf(integer -> integer != null, is(1))), tuple2 -> "To jest 1"),
        Case($(allOf(Objects::nonNull, is(312))), tuple2 -> "To jest 312")
    );

    System.out.println(allOf);

    String customFunctions = Match(1).of(
        Case($(integer -> (integer + 1 )/ 2 == 0), tuple2 -> "x + 1 / 2 == 0"),
        Case($(), tuple2 -> "Default")
    );

    System.out.println(customFunctions);

    User user = new User();

    String isUser = Match(user).of(
        Case($(u -> u.getId() != 0), tuple2 -> "Pusto"),
        Case($(), tuple2 -> "Coś tu mamy!")
    );

    System.out.println(isUser);
  }

  private static void exceptionMethod() {
    throw new RuntimeException();
  }
}
