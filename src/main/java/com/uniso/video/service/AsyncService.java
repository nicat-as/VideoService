package com.uniso.video.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.Future;

public interface AsyncService {
    Future<String> execute(MultipartFile multipartFile);
}
