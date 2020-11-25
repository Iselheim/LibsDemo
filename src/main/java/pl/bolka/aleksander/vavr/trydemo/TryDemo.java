package pl.bolka.aleksander.vavr.trydemo;

import io.vavr.control.Option;
import io.vavr.control.Try;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class TryDemo {

  public static void main(String[] args) {
    Try<Integer> tryDemo = Try.of(() -> 1 / 0);
    System.out.println(tryDemo);

    boolean success = tryDemo.isSuccess();
    tryDemo.getOrElse(0);
    tryDemo.getOrElse(() -> 0);
    Throwable cause = tryDemo.getCause();
    Integer orElseGet = tryDemo.getOrElseGet(throwable -> {
      throwable.printStackTrace();
      return 0;
    });

    tryDemo.onFailure(throwable -> {
      //do some code
    });

    tryDemo.onSuccess(integer -> {
      System.out.println(integer);
      // do some code
    });

    Try.withResources(() -> new BufferedInputStream(new InputStream() {
      @Override
      public int read() throws IOException {
        return 0;
      }
    })).of(bufferedInputStream -> {
      //file operation
      return null;
    });

    Option<Integer> integers = tryDemo.toOption();
    Optional<Integer> integer = tryDemo.toJavaOptional();

    Try<Integer> recover = tryDemo
        .recover(ArithmeticException.class, e -> 0)
        .recover(RuntimeException.class, e -> 1);
    System.out.println(recover);

    tryDemo.getOrElseThrow(() -> new RuntimeException());
    tryDemo.getOrElseThrow((throwable) -> new RuntimeException(throwable));

  }

}
