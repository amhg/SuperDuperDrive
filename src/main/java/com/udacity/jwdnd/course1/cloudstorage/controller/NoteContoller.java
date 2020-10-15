package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteContoller {

  @Autowired
  private NoteService noteService;

  @Autowired
  private UserService userService;

  @PostMapping("/notes")
  public String createOrUpdateNote(Authentication authentication, Note note) {
    User user = userService.getUser(authentication.getPrincipal().toString());

    if(note.getNoteId() > 0){
      noteService.updateNote(note);
    } else{
      noteService.addNote(note, user.getUserId());
    }
    return "redirect:/home";
  }

  @GetMapping("/deleteNote/{noteId}")
  public String deleteNote(@PathVariable int noteId){
    noteService.deleteNote(noteId);
    return "redirect:/home";
  }



}
