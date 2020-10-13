package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.FileDao;
import java.nio.file.Files;
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

  @Select("SELECT * FROM FILES")
  List<FileDao> getAllMessages();

  @Insert("INSERT INTO FILES (fileName, filePath) VALUES(#{fileName}, #{filePath})")
  @Options(useGeneratedKeys = true, keyProperty = "fileId")
  int addFile(FileDao fileDao);

  @Select("SELECT * FROM FILES WHERE fileId= #{fileId}")
  FileDao findFileById(String fileId);

  @Select("SELECT * FROM FILES WHERE userid = #{userid}")
  List<FileDao> findByUserId(int userid);

  @Delete("DELETE FROM FILES WHERE fileId= #{fileId}")
  boolean deleteNoteById(String id);
}
