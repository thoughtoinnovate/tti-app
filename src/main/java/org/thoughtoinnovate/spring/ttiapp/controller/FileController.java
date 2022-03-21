package org.thoughtoinnovate.spring.ttiapp.controller;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thoughtoinnovate.spring.ttiapp.entity.MyFile;
import org.thoughtoinnovate.spring.ttiapp.service.FileService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class FileController {

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/files/upload")
    public String uploadFile(@RequestParam String title, @RequestParam("file") MultipartFile file) throws IOException {
        return fileService.storeFile(title, file);
    }

    @GetMapping("/files/download/{id}")
    public void downloadFile(@PathVariable String id, HttpServletResponse httpServletResponse) throws IOException {
        MyFile file = fileService.getFile(id);
        FileCopyUtils.copy(file.getStream(), httpServletResponse.getOutputStream());
    }
}
