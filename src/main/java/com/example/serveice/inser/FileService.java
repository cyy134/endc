package com.example.serveice.inser;

import com.example.util.Msg;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    boolean getExcel(MultipartFile file) throws Exception;
    Msg getExcelOrder(String fileName, MultipartFile file) throws Exception;
}
