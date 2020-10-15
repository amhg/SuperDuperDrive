package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface NoteMapper {

  @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userid})")
  @Options(useGeneratedKeys = true, keyProperty = "noteId")
  int addNote(Note note);

  @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
  List<Note> findAllNotesByUserId(int userid);

  @Update("UPDATE NOTES SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription} WHERE noteId = #{noteId}")
  int updateNote(Note note);

  @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
  boolean deleteNoteById(int id);
}
