package com.example.myapp.controller;

import com.example.myapp.entity.Image;
import com.example.myapp.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final String FOLDER_PATH = "C:\\Asindu\\iCET\\MyApp\\";

    @Autowired
    private ImageRepository repo;

    @PostMapping
    public void addImage(@RequestParam(value = "image")MultipartFile file) throws IOException {
        String filePath = FOLDER_PATH+file.getOriginalFilename();
        repo.save(new Image(filePath));
        file.transferTo(new File(filePath));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getImage(@PathVariable(value = "id")int id) throws IOException {
        String imgUrl = repo.getImageById(id).getImgUrl();
        byte[] image = Files.readAllBytes(new File(imgUrl).toPath());
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/jpg")).body(image);
    }

    @GetMapping("/")
    public List<Image> getAllImages(){
        List<Image> ImageList = repo.findAll();
        return ImageList;
    }

}
