package com.udacity.jwdnd.course1.cloudstorage.pageObject;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import java.util.List;
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

  public HomePage(WebDriver webDriver) {

    PageFactory.initElements(webDriver, this);
    this.driver = webDriver;
  }

  public void changeToNoteNavigationTab()throws InterruptedException{
    navNotesTab.click();
    Thread.sleep(2000);

  }

  public void changeToCredentialNavigationTab()throws InterruptedException{
    navCredentialsTab.click();
    Thread.sleep(2000);
  }

  public void clickButtonAddNote() throws InterruptedException{
    buttonAddNote.click();
    Thread.sleep(3000);
  }

  public void clickButtonAddCredential() throws InterruptedException{
    buttonAddCredential.click();
    Thread.sleep(3000);
  }

  public void createOrEditNote(String title, String description) throws InterruptedException {
    String textInsideNoteTitle = noteTitle.getText();

    if (textInsideNoteTitle.length() >= 0) {
      noteTitle.clear();
      noteDescription.clear();
    }
    noteTitle.sendKeys(title);
    noteDescription.sendKeys(description);
    saveNote.click();
  }

  public void createOrEditCredential(String url, String username, String password) throws InterruptedException {
    String textInsideCredentialText = modalInputCredentialUrl.getText();

    if(textInsideCredentialText.length() >= 0){
      modalInputCredentialUrl.clear();
      modalInputCredentialUsername.clear();
      modalInputCredentialPassword.clear();
    }

    modalInputCredentialUrl.sendKeys(url);
    modalInputCredentialUsername.sendKeys(username);
    modalInputCredentialPassword.sendKeys(password);
    saveCredential.click();
    Thread.sleep(2000);
  }

  public void deleteNote(){
    deleteNoteId.click();
  }

  public void clickEditNote(){
    editNoteId.click();
  }

  public void logout(){
    logout.click();
  }

  public String getTableNoteTitle(){
    WebDriverWait wait = new WebDriverWait(driver, 40);
    wait.until(ExpectedConditions.elementToBeClickable(tableNoteTitle)).getText();
    return tableNoteTitle.getText();
  }

  public String getTableCredentialUrl(){
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
    editCredentialId.click();
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

  public void deleteCredentials(){

    try{
      WebElement body = credentialTable.findElement(By.tagName("tbody"));
      if(body != null){
        List<WebElement> rows = body.findElements(By.tagName("tr"));
        if(rows != null && !rows.isEmpty()){
          for(int i = 0; i < rows.size() - 1 ; i++){
            WebElement row = rows.get(i);
            Thread.sleep(2000);
            WebElement deleteButton = row.findElement(By.id("delete-credentialId"));
            Thread.sleep(2000);
            deleteButton.click();
          }
        }
      }

    }catch(NoSuchElementException | InterruptedException e){
      System.out.println("Element not found");
    }

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
