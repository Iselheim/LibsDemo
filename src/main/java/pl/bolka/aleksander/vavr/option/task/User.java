package pl.bolka.aleksander.vavr.option.task;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class User {

  private int id;

  private String name;

  private Address address;
}


