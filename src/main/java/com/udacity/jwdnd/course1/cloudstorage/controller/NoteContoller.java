package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteContoller {

  @Autowired
  private NoteService noteService;

  @PostMapping("/notes")
  public String createOrUpdateNote(Note note) {

    if(note.getNoteId() > 0){
      noteService.updateNote(note);
    } else{
      noteService.addNote(note);
    }
    return "redirect:/home";
  }

  @GetMapping("/deleteNote/{noteId}")
  public String deleteNote(@PathVariable int noteId){
    noteService.deleteNote(noteId);
    return "redirect:/home";
  }



}
