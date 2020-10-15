package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

  @Autowired
  private NoteMapper noteMapper;

  public int addNote(Note note, int userid){
    note.setUserid(userid);
    return noteMapper.addNote(note);
  }

  public List<Note> getAllNotesByUserId(int userid){
    return noteMapper.findAllNotesByUserId(userid);
  }

  public int updateNote(Note note){
    return noteMapper.updateNote(note);
  }

  public boolean deleteNote(int noteId){
    return noteMapper.deleteNoteById(noteId);
  }

}
