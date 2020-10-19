package com.udacity.jwdnd.course1.cloudstorage.pageObject;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

  @FindBy(css="#logout-link")
  private WebElement logout;

  @FindBy(css="#nav-notes-tab")
  private WebElement navNotesTab;

  @FindBy(css="#nav-credentials-tab")
  private WebElement navCredentialsTab;

  @FindBy(id="add-noteId")
  private WebElement buttonAddNote;

  @FindBy(id="add-CredentialId")
  private WebElement buttonAddCredential;

  @FindBy(css="#note-title")
  private WebElement noteTitle;

  @FindBy(css="#note-description")
  private WebElement noteDescription;

  @FindBy(css="#save-changes")
  private WebElement saveNote;

  @FindBy(css="#table-noteTitle")
  private WebElement tableNoteTitle;

  @FindBy(id="edit-noteId")
  private WebElement editNoteId;

  @FindBy(id="delete-noteId")
  private WebElement deleteNoteId;

  @FindBy(id="table-trId")
  private WebElement tableTrId;

  @FindBy(css="#credential-url")
  private WebElement modalInputCredentialUrl;

  @FindBy(css="#credential-username")
  private WebElement modalInputCredentialUsername;

  @FindBy(css="#credential-password")
  private WebElement modalInputCredentialPassword;

  @FindBy(css="#save-credentialChangesId")
  private WebElement saveCredential;

  @FindBy(css="#table-credentialUrl")
  private WebElement tableCredentialUrl;

  @FindBy(css="#credentialTable")
  private WebElement credentialTable;

  @FindBy(css="#edit-credentialId")
  private WebElement editCredentialId;

  @FindBy(id="delete-credentialId")
  private WebElement deleteCredentialId;

  private WebDriver driver;

  private  WebDriverWait wait;

  public HomePage(WebDriver webDriver) {
    PageFactory.initElements(webDriver, this);
    this.driver = webDriver;
    this.wait = new WebDriverWait (driver, 30);
  }

  public void changeToNoteNavigationTab(){
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    wait.until(ExpectedConditions.elementToBeClickable(navNotesTab)).click();
  }

  public void changeToCredentialNavigationTab() {
    wait.until(ExpectedConditions.elementToBeClickable(navCredentialsTab)).click();
  }

  public void clickButtonAddNote() {
    wait.until(ExpectedConditions.elementToBeClickable(buttonAddNote)).click();
  }

  public void clickButtonAddCredential() {
    wait.until(ExpectedConditions.elementToBeClickable(buttonAddCredential)).click();
  }

  public void createOrEditNote(String title, String description) {
    String textInsideNoteTitle = noteTitle.getText();

    if (textInsideNoteTitle.length() >= 0) {
      noteTitle.clear();
      noteDescription.clear();
    }
    wait.until(ExpectedConditions.elementToBeClickable(noteTitle)).sendKeys(title);
    wait.until(ExpectedConditions.elementToBeClickable(noteDescription)).sendKeys(description);
    wait.until(ExpectedConditions.elementToBeClickable(saveNote)).click();

  }

  public void createOrEditCredential(String url, String username, String password) {
    String textInsideCredentialText = modalInputCredentialUrl.getText();

    if(textInsideCredentialText.length() >= 0){
      modalInputCredentialUrl.clear();
      modalInputCredentialUsername.clear();
      modalInputCredentialPassword.clear();
    }
    wait.until(ExpectedConditions.elementToBeClickable(modalInputCredentialUrl)).click();
    modalInputCredentialUrl.sendKeys(url);
    wait.until(ExpectedConditions.elementToBeClickable(modalInputCredentialUsername)).sendKeys(username);
    wait.until(ExpectedConditions.elementToBeClickable(modalInputCredentialPassword)).sendKeys(password);
    wait.until(ExpectedConditions.elementToBeClickable(saveCredential)).click();

  }

  public void deleteNote(){
    WebDriverWait wait = new WebDriverWait (driver, 20);
    wait.until(ExpectedConditions.elementToBeClickable(deleteNoteId)).click();
  }

  public void clickEditNote(){
    wait.until(ExpectedConditions.elementToBeClickable(editNoteId)).click();
  }

  public void logout(){
    logout.click();
  }

  public String getTableNoteTitle(){
    wait.until(ExpectedConditions.elementToBeClickable(tableNoteTitle)).getText();
    return tableNoteTitle.getText();
  }

  public String getTableCredentialUrl(){
    wait.until(ExpectedConditions.elementToBeClickable(tableCredentialUrl)).getText();
    return tableCredentialUrl.getText();
  }

  public WebElement getCredentialTable() {
    return credentialTable;
  }

  public boolean doesNoteExist(){
    return (doesElementExist(driver,"table-noteTitle") && doesElementExist(driver, "table-noteDescriptionId"));

  }

  public boolean doesElementExist(WebDriver driver, String identifier){
    try{
      driver.findElement(By.id(identifier));
      return true;
    } catch(NoSuchElementException e){
      System.out.println(identifier + " -> element not found");
      return false;
    }
  }

  public void clickToEditCredential(){
    wait.until(ExpectedConditions.elementToBeClickable(editCredentialId)).click();
  }

  public boolean checkForCredentialEncryptedPassword(String url, String username, CredentialService credentialService){
    return getCredentialTableRowAlternative(url, username, credentialService) != null;

  }

  private WebElement getCredentialTableRowAlternative(String url, String username, CredentialService credentialService){
    WebElement credentialRow = null;
    try{
      WebElement body = credentialTable.findElement(By.tagName("tbody"));
      if(body != null){
        List<WebElement> rows = body.findElements(By.tagName("tr"));
        if(rows != null && !rows.isEmpty()){
          for(int i = 0; i < rows.size(); i++){
            Credential credential = credentialService.getCredentialById(i + 1);
            WebElement row = rows.get(i);
            WebElement textCredentialUrl = row.findElement(By.id("table-credentialUrl"));
            WebElement textCredentialUsername = row.findElement(By.id("table-credentialUsername"));
            WebElement textCredentialPassword = row.findElement(By.id("table-credentialPassword"));
            System.out.println("CREDENTIAL ENCRYPTED PASSWORD: " + credential.getPassword());
            System.out.println("CREDENTIAL ENCRYPTED PASSWORD TABLE: " + textCredentialPassword.getAttribute("innerHTML"));
            if (textCredentialUrl.getAttribute("innerHTML").equals(url) &&
                textCredentialUsername.getAttribute("innerHTML").equals(username) &&
                textCredentialPassword.getAttribute("innerHTML").equals(credential.getPassword())) {
              credentialRow = row;
              break;
            }
          }
        }
      }

    }catch(NoSuchElementException e){
      System.out.println("Element not found");
    }
    return credentialRow;
  }

  public void deleteOneCredential(){
    try{
      WebElement body = credentialTable.findElement(By.tagName("tbody"));
      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      if(body != null){
        List<WebElement> credentials = body.findElements(By.tagName("td"));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement deleteElement = null;
        if(credentials != null && !credentials.isEmpty()){
          for(int i = 0; i < credentials.size() ; i++){
            WebElement credential = credentials.get(i);
            deleteElement = credential.findElement(By.name("delete"));
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            if (deleteElement != null){
              break;
            }
          }
        }
        wait.until(ExpectedConditions.elementToBeClickable(deleteElement)).click();
      }

    }catch(NoSuchElementException e){
      System.out.println("Element not found");
    }

  }

  public int getCredentialTableSize() {
    List<WebElement> credentials = new ArrayList<>();
    try {
      WebElement body = credentialTable.findElement(By.tagName("tbody"));
      if (body != null) {
        credentials = body.findElements(By.tagName("tr"));
      }

    } catch (NoSuchElementException e) {
      System.out.println("Element not found");
    }

    return credentials.size();
  }




  public WebElement getModalInputCredentialPassword() {
    return modalInputCredentialPassword;
  }




  private void waitForVisibility(WebElement element) throws Error{
    new WebDriverWait(driver, 60)
        .until(ExpectedConditions.visibilityOf(element));
  }

  public void deleteCredential() {
    deleteCredentialId.click();
  }
}
