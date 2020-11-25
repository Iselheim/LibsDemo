package pl.bolka.aleksander.vavr.tuple.task;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import io.vavr.Tuple2;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import pl.bolka.aleksander.vavr.option.task.Job;
import pl.bolka.aleksander.vavr.option.task.User;

public class TupleTaskTest {

  @Test
  public void getUserSalary() {

    User user = User.builder()
                    .name("Janek")
                    .build();
    user.setJobList(Arrays.asList(
        new Job(BigDecimal.TEN),
        new Job(BigDecimal.ONE)));

    User user2 = User.builder()
                    .name("Franek")
                    .build();
    user2.setJobList(Arrays.asList(
        new Job(BigDecimal.valueOf(1233)),
        new Job(BigDecimal.valueOf(111))));

    User user3 = User.builder()
                    .name("Brajanek")
                    .build();
    user3.setJobList(Arrays.asList(
        new Job(BigDecimal.valueOf(123)),
        new Job(BigDecimal.valueOf(321))));
    List<User> users = Arrays.asList(user, user2, user3);
    TupleTask tupleTask = new TupleTask();
    List<Tuple2<User, BigDecimal>> usersSalary = tupleTask.getUsersSalary(users);

    assertThat(usersSalary, is(not(nullValue())));
    assertThat(usersSalary.size(), is(equalTo(3)));

    Tuple2<User, BigDecimal> user1Salary = usersSalary.get(0);
    assertThat(user1Salary._1, is(equalTo(user)));
    assertThat(user1Salary._2, is(equalTo(BigDecimal.valueOf(11))));

    Tuple2<User, BigDecimal> user2Salary = usersSalary.get(1);
    assertThat(user2Salary._1, is(equalTo(user2)));
    assertThat(user2Salary._2, is(equalTo(BigDecimal.valueOf(1344))));

    Tuple2<User, BigDecimal> user3Salary = usersSalary.get(2);
    assertThat(user3Salary._1, is(equalTo(user3)));
    assertThat(user3Salary._2, is(equalTo(BigDecimal.valueOf(444))));
  }

}
