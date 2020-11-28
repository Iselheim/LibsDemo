package pl.bolka.aleksander.vavr.trydemo.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import pl.bolka.aleksander.vavr.option.task.User;

public class TryTask {

  public User readUser(String path) {

    try {
      URL resource = getClass().getClassLoader().getResource(path);
      File file = new File(resource.toURI());
      User user = new ObjectMapper().readValue("content", User.class);
    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    return null;
  }

}
