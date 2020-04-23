package com.example.serveice.inser;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    boolean getExcel(MultipartFile file) throws Exception;
}
