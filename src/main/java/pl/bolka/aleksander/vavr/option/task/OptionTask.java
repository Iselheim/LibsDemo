package pl.bolka.aleksander.vavr.option.task;

public class OptionTask {

  public String getStreetByUserId(int id){
    UserService userService = new UserService();
    User user = userService.getUser(id);
    if (user != null) {
      Address address = user.getAddress();
      if (null != address) {
        return address.getStreet();
      }
      else {
        return "";
      }
    } else {
      return "";
    }
  }

}
