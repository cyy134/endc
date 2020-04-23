package com.example.serveice.imp;

import com.example.model.User;
import com.example.serveice.inser.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class FielServiceImp implements FileService {

    @Override
    public boolean batchImport(String fileName, MultipartFile file) throws Exception {
        Boolean notnull = false;
        List<User> userList = new ArrayList<>();
        if(!fileName.matches("^.+\\.(?i)(xls)$")&& !fileName.matches("^.+\\.(?i)(xlsx)$")){
            
        }
        return false;
    }
}
