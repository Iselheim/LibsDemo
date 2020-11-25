package pl.bolka.aleksander.vavr.option.task;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class OptionTaskTest {

  @Test
  public void getStreetByUserId() {
    OptionTask optionTask = new OptionTask();

    assertThat(optionTask.getStreetByUserId(1), is(equalTo("Krakowska")));

    assertThat(optionTask.getStreetByUserId(2), is(equalTo("")));

    assertThat(optionTask.getStreetByUserId(3), is(equalTo("")));

  }

}
