package pl.bolka.aleksander.vavr.option.task;

public class UserService {

  public User getUser(int id){
    if (id < 3) {
      User.UserBuilder userBuilder = User.builder()
                                  .id(id)
                                  .name("Test");
      if (id == 1) {
        userBuilder.address(Address.builder()
                                   .street("Krakowska")
                                   .build());
      }
      return userBuilder.build();
    } else {
      return null;
    }
  }

}
