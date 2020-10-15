package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
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

  @Autowired
  private UserService userService;


  @GetMapping("/home")
  public ModelAndView getHomePage(Authentication authentication, ModelAndView modelAndView)
      throws Exception {
    User user = userService.getUser(authentication.getPrincipal().toString());

    modelAndView.addObject("username", user.getUsername());
    modelAndView.addObject("files", fileService.getAllFilesByUserId(user.getUserId()));
    modelAndView.addObject("notes", noteService.getAllNotesByUserId(user.getUserId()));
    modelAndView.addObject("credentials", credentialService.getAllCredentialsByUserId(user.getUserId()));
    modelAndView.addObject("encryptionService", encryptionService);
    return modelAndView;
  }


}
