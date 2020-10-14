package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileController {

  @Autowired
  FileService fileService;

  @PostMapping("/uploadFile")
  public String validateAndSaveFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile file, RedirectAttributes redirectAttributes){
    String user = authentication.getName();
    if(file.isEmpty()){
      redirectAttributes.addFlashAttribute("errorMessage",  "No File Selected, please try again");
      return "redirect:/home";
    }

    if(fileService.validateFile(file)){
      redirectAttributes.addFlashAttribute("errorMessage",  "File already exists, please try again");
      return "redirect:/home";
    }

    fileService.saveFile(file);
    return "redirect:/home";

  }

  @GetMapping("/view/{fileId}")

  public ResponseEntity<byte[]> getAndDisplayFile(@PathVariable String fileId) throws IOException {

    File fileDao = fileService.getFile(fileId);
    byte[] data = Files.readAllBytes(Paths.get(fileDao.getFilePath()));

    java.io.File file = new java.io.File(fileDao.getFilePath());
    String name = file.getName();
    String mime = Files.probeContentType( Paths.get(file.getPath()));

    HttpHeaders headers=new HttpHeaders();
    headers.setContentType(org.springframework.http.MediaType.parseMediaType(mime));
    headers.add("Content-Disposition", "inline;filename=" + name);
    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

    return new ResponseEntity(data, headers, HttpStatus.OK);
  }

  @GetMapping("/deleteFile/{fileId}")
  public String deleteFile(@PathVariable String fileId){
    fileService.deleteFile(fileId);
    return "redirect:/home";
  }

}
