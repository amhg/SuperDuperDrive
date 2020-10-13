package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
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
  public ModelAndView getHomePage(ModelAndView modelAndView) {
/*  public ModelAndView getHomePage(Authentication authentication, ModelAndView modelAndView) {
    String userName = (String) authentication.getPrincipal();
    modelAndView.addObject("userName", userName);*/
    modelAndView.addObject("files", fileService.getAllFiles());
    modelAndView.addObject("notes", noteService.getAllNotes());
    modelAndView.addObject("credentials", credentialService.getAllCredentials());
    //modelAndView.addObject("encryptionService", encryptionService);
    return modelAndView;
  }


}
