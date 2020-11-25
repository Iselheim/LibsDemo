package pl.bolka.aleksander.vavr.option.task;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Setter
@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class User {

  private int id;

  private String name;

  private Address address;

  private List<Job> jobList= new ArrayList<>();
}


