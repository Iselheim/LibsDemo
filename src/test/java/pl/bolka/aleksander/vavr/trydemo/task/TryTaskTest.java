package pl.bolka.aleksander.vavr.trydemo.task;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import org.junit.Test;
import pl.bolka.aleksander.vavr.option.task.Address;
import pl.bolka.aleksander.vavr.option.task.Job;
import pl.bolka.aleksander.vavr.option.task.User;

public class TryTaskTest {

  @Test
  public void readUser() {

    User user = new TryTask().readUser("user.txt");

    User build = User.builder()
                     .name("Benny")
                     .address(Address.builder()
                                     .street("Hill")
                                     .build())
                     .id(1)
                     .jobList(Arrays.asList(new Job(BigDecimal.TEN)))
                     .build();

    assertEquals(user, build);
  }
}
