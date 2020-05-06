package com.xebia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


@SpringBootApplication
public class CovidTrackerApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(CovidTrackerApplication.class, args);
    context.getBean(DispatcherServlet.class).setThrowExceptionIfNoHandlerFound(true);
  }

}
