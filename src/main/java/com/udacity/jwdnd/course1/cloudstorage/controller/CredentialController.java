package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialController {

  @Autowired
  private CredentialService credentialService;

  @Autowired
  private EncryptionService encryptionService;

  @PostMapping("/credentials")
  public String encryptAndAddOrUpdateCredential(Credential credential){
    Credential encryptedCredential = encryptCredential(credential);

    if(credential.getCredentialid() != null){
      credentialService.updateCredential(encryptedCredential);
    }else{
      credentialService.addCredential(encryptedCredential);
    }
    return "redirect:/home";
  }

  @GetMapping("/deleteCredential/{credentialId}")
  public String deleteCredential(@PathVariable int credentialId){
    credentialService.deleteCredential(credentialId);
    return "redirect:/home";
  }

  private Credential encryptCredential(Credential credential) {
    String password = credential.getPassword();
    SecureRandom random = new SecureRandom();
    byte[] key = new byte[16];
    random.nextBytes(key);
    String encodedKey = Base64.getEncoder().encodeToString(key);
    String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
    credential.setPassword(encryptedPassword);
    credential.setCredentialKey(encodedKey);
    return credential;
  }

}
