package com.udacity.jwdnd.course1.cloudstorage;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.pageObject.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pageObject.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pageObject.SignupPage;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

/**
 * ValidationTest performs the tests:
 * - Validate Sign up/ login and Home page Access with login and Invalid Home Access when user has logged out
 * - Tests for Note Creation, Viewing, Editing, and Deletion
 * - Tests for Credential Creation, Viewing, Editing, and Deletion
 */

//TODO separate classes
  // TODO add wait.until
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ValidationTest {

  @LocalServerPort
  public int port;

  public static WebDriver driver;
  public String baseURL;
  private static SignupPage signupPage;
  private static LoginPage loginPage;
  private static HomePage homePage;

  @Autowired
  private CredentialService credentialService;


  @BeforeAll
  public static void beforeAll() {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    signupPage = new SignupPage(driver);
    loginPage = new LoginPage(driver);
    homePage = new HomePage(driver);
  }

  @AfterAll
  public static void afterAll() {
    driver.quit();
    driver = null;
  }

  @BeforeEach
  public void beforeEach() {

    baseURL = baseURL = "http://localhost:" + port;
  }

  @Test
  public void whenUserSignUpLogin_ThenValidateAndInvalidateHomePageAccess() throws InterruptedException {
    String username = "user1";
    String password = "password1";

    driver.get(baseURL + "/signup");

    signupPage.signup("Luis", "Halley", username, password);

    driver.get(baseURL + "/login");

    loginPage.login(username, password);

    driver.get(baseURL + "/home");
    assertEquals("Home", driver.getTitle());

    homePage.logout();

    driver.get(baseURL + "/home");
    assertEquals("Login", driver.getTitle());
  }

  @Test
  public void whenNoteCreated_ThenValidateNoteExists() throws InterruptedException {

    userLoginProcess();

    homePage.changeToNoteNavigationTab();

    homePage.clickButtonAddNote();

    homePage.createOrEditNote("noteTitle", "noteDescription");

    Thread.sleep(3000);

    homePage.changeToNoteNavigationTab();

    Thread.sleep(3000);

    assertEquals("noteTitle", homePage.getTableNoteTitle());

  }

  @Test
  public void whenNoteEdited_ThenValidateNoteChanges() throws InterruptedException {

    userLoginProcess();

    createNoteProcess();

    homePage.clickEditNote();

    Thread.sleep(3000);

    homePage.createOrEditNote("newTitle", "newDescription");

    Thread.sleep(3000);

    homePage.changeToNoteNavigationTab();

    //Thread.sleep(3000);

    assertEquals("newTitle", homePage.getTableNoteTitle());

  }

  @Test
  public void whenNoteDeleted_ThenValidateNoteDeletion() throws InterruptedException {

    userLoginProcess();

    createNoteProcess();

    homePage.deleteNote();

    Thread.sleep(2000);

    homePage.changeToNoteNavigationTab();

    Thread.sleep(3000);

    assertFalse(homePage.doesNoteExist());

  }

  @Test
  public void whenCredentialCreated_ThenValidateCredentials() throws InterruptedException {
    userLoginProcess();

    homePage.changeToCredentialNavigationTab();

    Thread.sleep(3000);

    homePage.clickButtonAddCredential();

    homePage.createOrEditCredential("url1", "user1", "password1");

    Thread.sleep(2000);

    homePage.changeToCredentialNavigationTab();

    assertEquals("url1", homePage.getTableCredentialUrl());

  }

  //TODO add more credenials
  @Test
  public void whenCredentialIsCreated_ThenValidatePasswordIsEncrypted() throws InterruptedException {
    userLoginProcess();

    homePage.changeToCredentialNavigationTab();

    Thread.sleep(3000);

    homePage.clickButtonAddCredential();

    homePage.createOrEditCredential("url1", "user1", "password1");

    Thread.sleep(2000);

    homePage.changeToCredentialNavigationTab();

    assertTrue( homePage.checkForCredentialEncryptedPassword("url1", "user1", credentialService));

  }

  @Test
  public void whenEditCredential_ThenValidatePasswordIsUnencrypted()
      throws InterruptedException {
    WebDriverWait wait = new WebDriverWait (driver, 30);

    userLoginProcess();

    homePage.changeToCredentialNavigationTab();

    Thread.sleep(3000);

    homePage.clickButtonAddCredential();

    homePage.createOrEditCredential("url1", "user1", "password1");

    Thread.sleep(2000);

    homePage.changeToCredentialNavigationTab();

    homePage.clickToEditCredential();

    WebElement modalInputCredentialPassword = homePage.getModalInputCredentialPassword();
    wait.until(ExpectedConditions.elementToBeClickable(modalInputCredentialPassword));

    String decryptedPassword = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value", modalInputCredentialPassword);
    String scriptHtml = "return document.getElementById('credential-id').getAttribute('value');";
    int credentialId = Integer.parseInt( ((JavascriptExecutor) driver).executeScript(scriptHtml).toString());
    Credential credential = credentialService.getCredentialById(credentialId);
    System.out.println("DECRYPTED PASSWORD: " + decryptedPassword);
    System.out.println("INPUT CREDENTIAL ID: " + credentialId);
    System.out.println("CREDENTIAL UNINCRYPTED PASSWORD: " + credential.getPassword());
    Assertions.assertEquals("password1", decryptedPassword);

  }

  @Test
  public void whenEditCredential_ThenValidateCredentialIsEdited()
      throws InterruptedException {

    WebDriverWait wait = new WebDriverWait (driver, 30);

    userLoginProcess();

    homePage.changeToCredentialNavigationTab();

    Thread.sleep(2000);

    homePage.clickButtonAddCredential();

    homePage.createOrEditCredential("url1", "user1", "password1");

    Thread.sleep(2000);

    homePage.changeToCredentialNavigationTab();

    homePage.clickToEditCredential();
    Thread.sleep(2000);

    homePage.createOrEditCredential("urlNew", "user1New", "password1New");

    Thread.sleep(2000);

    homePage.changeToCredentialNavigationTab();

    assertEquals("urlNew", homePage.getTableCredentialUrl());

  }

  @Test
  public void whenCredentialsDeleted_ThenVerifyCredentialDeletion() throws InterruptedException {
    WebDriverWait wait = new WebDriverWait (driver, 30);

    userLoginProcess();

    homePage.changeToCredentialNavigationTab();

    Thread.sleep(1000);

    for(int i=0; i < 3; i++){
      homePage.clickButtonAddCredential();
      homePage.createOrEditCredential("url" + i, "user" + i, "password" + i);
      homePage.changeToCredentialNavigationTab();
    }

    homePage.deleteCredentials();

    homePage.changeToCredentialNavigationTab();

  }





  private void userLoginProcess() {

    String username = "user1";
    String password = "password1";

    driver.get(baseURL + "/signup");

    signupPage.signup("Luis", "Halley", username, password);

    driver.get(baseURL + "/login");

    loginPage.login(username, password);

    driver.get(baseURL + "/home");

  }

  private void createNoteProcess() throws InterruptedException {

    homePage.changeToNoteNavigationTab();

    homePage.clickButtonAddNote();

    homePage.createOrEditNote("noteTitle", "noteDescription");

    Thread.sleep(3000);

    homePage.changeToNoteNavigationTab();

  }



}
