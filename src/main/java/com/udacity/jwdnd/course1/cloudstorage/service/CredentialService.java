package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {

  @Autowired
  private CredentialMapper credentialMapper;

  public int addCredential(Credential credential){
    return credentialMapper.addCredential(credential);
  }

  public List<Credential> getAllCredentials(){
    return credentialMapper.getAllCredentials();
  }

  public Credential getCredentialById(Integer credentialid){
    return credentialMapper.getCredentialById(credentialid);
  }

  public int updateCredential(Credential credential){
    return credentialMapper.updateCredential(credential);
  }

  public boolean deleteCredential(int credentialId){
    return credentialMapper.deleteCredentialById(credentialId);
  }

}
