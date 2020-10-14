package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

  @Autowired
  private FileService fileService;

  @Autowired
  private NoteService noteService;

  @Autowired
  private CredentialService credentialService;

  @Autowired
  private EncryptionService encryptionService;


  @GetMapping("/home")
  public ModelAndView getHomePage(Authentication authentication, ModelAndView modelAndView) {
    String username = authentication.getName();
    modelAndView.addObject("files", fileService.getAllFiles());
    modelAndView.addObject("notes", noteService.getAllNotes());
    modelAndView.addObject("credentials", credentialService.getAllCredentials());
    modelAndView.addObject("encryptionService", encryptionService);
    return modelAndView;
  }


}
