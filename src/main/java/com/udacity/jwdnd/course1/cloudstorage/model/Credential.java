package com.udacity.jwdnd.course1.cloudstorage.model;


public class Credential {

  private int credentialid;
  private String url;
  private String username;
  private String password;
  private String credentialKey;

  public int getCredentialid() {
    return credentialid;
  }

  public void setCredentialid(int credentialid) {
    this.credentialid = credentialid;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getCredentialKey() {
    return credentialKey;
  }

  public void setCredentialKey(String credentialKey) {
    this.credentialKey = credentialKey;
  }
}
