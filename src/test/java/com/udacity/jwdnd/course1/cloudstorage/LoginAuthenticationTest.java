package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LoginAuthenticationTest {
  @LocalServerPort
  private Integer port;

  private static WebDriver driver;

  @BeforeAll
  public static void beforeAll() {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
  }

  @AfterAll
  public static void afterAll() {
    driver.quit();
  }

  @Test
  public void getLoginPage(){
    driver.get("http://localhost:" + this.port + "/login");
    Assertions.assertEquals("Login", driver.getTitle());
  }

  @Test
  public void whenUnauthorizedUser_CallsHomePage_ThenReturnLoginPage(){
    driver.get("http://localhost:" + this.port + "/home");
    Assertions.assertEquals("Login", driver.getTitle());
  }

  @Test
  public void whenUnauthorizedUser_CallsSignUpPage_ThenReturnSignUpPage(){
    driver.get("http://localhost:" + this.port + "/signup");
    Assertions.assertEquals("Sign Up", driver.getTitle());
  }


}
