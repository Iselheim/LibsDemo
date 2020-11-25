package pl.bolka.aleksander.vavr.option;

import io.vavr.control.Option;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

public class OptionDemo {

  public static void main(String[] args) {

    OptionDemo demo = new OptionDemo();
    demo.run();
  }

  public void run() {
    // różnice miedzy Option a Optional
//    vavrVsJava();

    // pobieranie wartości
//    optionGets();

    // różnica miedzy map
//    mapDifference();

    // róznice w obsłudze peek
//    peekDemo();

  }

  private void vavrVsJava() {
    Option<Object> noneOption = Option.of(null);
    Option<Object> someOption = Option.of("some");

    System.out.println(noneOption);
    System.out.println(someOption);

    Optional<Object> noneOptional = Optional.of(null);
    Optional<Object> someOptional = Optional.of("some");

    System.out.println(noneOptional);
    System.out.println(someOptional);
  }

  private void optionGets() {
    Option<String> noneOption = Option.of(null);
    String some = noneOption.getOrElse("Some");
    System.out.println(some);
    String orElse = noneOption.getOrElse(() -> "Some");
    System.out.println(orElse);
    String orNull = noneOption.getOrNull();
    System.out.println(orNull);
    String orElseTry = noneOption.getOrElseTry(this::functionWithException);
    System.out.println(orElseTry);
    String orElseThrow = noneOption.getOrElseThrow(() ->
        new RuntimeException("This field can't be null"));
    System.out.println(orElseThrow);
  }

  private void mapDifference() {
    Optional<String> optionalSome = Optional.of("Some");
    Optional<String> optionalS = optionalSome.map(s -> (String) null)
                                             .map(s -> s.toUpperCase());
    System.out.println(optionalS);
    Option<String> optionSome = Option.of("Some");
    Option<String> nullValue = optionSome.map(s -> (String) null)
                                         .flatMap(s -> Option.of(s)
                                                             .map(s1 -> s1.toUpperCase()));
    System.out.println(nullValue);

    optionSome.map(s -> (String) null)
              .map(s1 -> s1.toUpperCase());

  }

  private void peekDemo() {
    Option<String> some1 = Option.of("Some")
                                 .peek(System.out::println)
                                 .peek(s -> System.out.println(1 + 1))
                                 .map(String::toUpperCase)
                                 .peek(System.out::println);

    Stream<String> some = Optional.of("Some").stream()
                                  .peek(System.out::println)
                                  .peek(s -> System.out.println(1 + 1))
                                  .map(String::toUpperCase)
                                  .peek(System.out::println);
  }

  private String functionWithException() throws IOException {
    System.out.println("into functionWithException");
    throw new IOException();
  }
}
