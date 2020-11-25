package pl.bolka.aleksander.vavr.trydemo.task;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import pl.bolka.aleksander.vavr.option.task.User;

public class TryTask {

  public User readUser(String path) {

    try {
      URL resource = getClass().getClassLoader().getResource(path);
      File file = new File(resource.toURI());

    } catch (URISyntaxException e) {
      e.printStackTrace();
    }

    return null;
  }

}
