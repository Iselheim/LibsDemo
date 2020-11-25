package pl.bolka.aleksander.vavr.option.task;

import java.util.ArrayList;
import java.util.List;
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

  private List<Job> jobList= new ArrayList<>();
}


