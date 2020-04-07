package com.uniso.video.service;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    void init();
    String store(MultipartFile multipartFile);
    void deleteFile(String dir);
    void deleteAll();
    Stream<Path> loadAll();
}
