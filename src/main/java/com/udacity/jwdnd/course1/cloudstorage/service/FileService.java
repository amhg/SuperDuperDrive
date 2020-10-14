package com.udacity.jwdnd.course1.cloudstorage.service;


import com.udacity.jwdnd.course1.cloudstorage.exception.FileStorageException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
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

    for(File file: getAllFiles()){
      String userFileName = StringUtils.cleanPath(originalFilename.getOriginalFilename());
      if(file.getFileName().equalsIgnoreCase(userFileName)){
        return true;
      }
    }
    return false;
  }

  public void saveFile(MultipartFile file) {
    try{
      Path copyLocation = Paths.get(uploadDir + java.io.File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
      Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

      File fileDao = new File();
      fileDao.setFileName(file.getOriginalFilename());
      fileDao.setFilePath(copyLocation.toString());
      this.fileMapper.addFile(fileDao);

    } catch(Exception e){
      e.printStackTrace();
      throw new FileStorageException("Could not store file " + file.getOriginalFilename() + ". Please try again");
    }
  }

  public List<File> getAllFiles(){
    //List<File> files = fileMapper.findByUserId(userid);
    return fileMapper.getAllFiles();
  }

  public List<File> getAllFilesByUserId(int userid) throws Exception {
    List<File> files = fileMapper.findByUserId(userid);
    if(files == null){
      throw new Exception();
    }
    return files;
  }


  public File getFile(String fileId) {
    return fileMapper.findFileById(fileId);
  }

  public boolean deleteFile(String fileId) {
    return fileMapper.deleteNoteById(fileId);
  }
}

