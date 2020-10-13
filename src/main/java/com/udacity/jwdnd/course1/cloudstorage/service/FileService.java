package com.udacity.jwdnd.course1.cloudstorage.service;


import com.udacity.jwdnd.course1.cloudstorage.exception.FileStorageException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.FileDao;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

  @Autowired
  FileMapper fileMapper;

  @Value("${app.upload.dir:${user.home}}")
  public String uploadDir;

  public boolean validateFile(MultipartFile originalFilename) {

    for(FileDao file: getAllFiles()){
      String userFileName = StringUtils.cleanPath(originalFilename.getOriginalFilename());
      if(file.getFileName().equalsIgnoreCase(userFileName)){
        return true;
      }
    }
    return false;
  }

  public void saveFile(MultipartFile file) {
    try{
      Path copyLocation = Paths.get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
      Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

      FileDao fileDao = new FileDao();
      fileDao.setFileName(file.getOriginalFilename());
      fileDao.setFilePath(copyLocation.toString());
      this.fileMapper.addFile(fileDao);

    } catch(Exception e){
      e.printStackTrace();
      throw new FileStorageException("Could not store file " + file.getOriginalFilename() + ". Please try again");
    }
  }

  public List<FileDao> getAllFiles(){
    return fileMapper.getAllMessages();
  }

  public List<FileDao> getAllFilesByUserId(int userid) throws Exception {
    List<FileDao> files = fileMapper.findByUserId(userid);
    if(files == null){
      throw new Exception();
    }
    return files;
  }


  public FileDao getFile(String fileId) {
    return fileMapper.findFileById(fileId);
  }

  public boolean deleteFile(String fileId) {
    return fileMapper.deleteNoteById(fileId);
  }
}

