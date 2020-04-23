package com.example.serveice.imp;

import com.example.dao.FileMapper;
import com.example.model.User;
import com.example.serveice.inser.FileService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

//上传excel表数据到数据库（没有验证次方法）
@Service
public class FielServiceImp implements FileService {

    @Autowired
    FileMapper fileMapper;

    @Override
    public boolean getExcel(MultipartFile file) throws Exception {
        List<User> userList = new ArrayList<>();
        //得到上传的表
        Workbook workbook2 = WorkbookFactory.create(file.getInputStream());
        //获取表面为test的工作表
        Sheet sheet2 = workbook2.getSheet("test");
        //总行数
        int num = sheet2.getLastRowNum();
        //总列数
        int col = sheet2.getRow(0).getLastCellNum();
        String[] valus = new String[10];

        for (int i=0; i<=num; i++){
            Row row = sheet2.getRow(i);
            for(int j=0; j<=col; j++) {
                Cell cell1 = row.getCell(j);
                cell1.setCellType(CellType.STRING);
                valus[j]  = cell1.getStringCellValue();
            }
            User user = new User();
            user.setAcount(valus[0]);
            user.setAge(valus[1]);
            user.setCreatetime(valus[2]);
            user.setEmil(valus[3]);
            user.setNickName(valus[4]);
            user.setPhone(valus[5]);
            user.setSsex(valus[6]);

            fileMapper.addUser(user);
        }
        return true;
    }
}
