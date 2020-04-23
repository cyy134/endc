package com.example.serveice.inser;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    boolean batchImport(String fileName, MultipartFile file) throws Exception;
}
