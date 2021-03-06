package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface FileMapper {

  @Insert("INSERT INTO FILES (fileName, filePath, userid) VALUES(#{fileName}, #{filePath}, #{userid})")
  @Options(useGeneratedKeys = true, keyProperty = "fileId")
  int addFile(File file);

  @Select("SELECT * FROM FILES WHERE fileId= #{fileId}")
  File findFileByFileId(String fileId);

  @Select("SELECT * FROM FILES WHERE userid = #{userid}")
  List<File> findAllFilesByUserId(int userid);

  @Delete("DELETE FROM FILES WHERE fileId= #{fileId}")
  boolean deleteNoteById(String id);
}
