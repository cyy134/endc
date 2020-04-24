package com.example.serveice.imp;

import com.example.dao.FileMapper;
import com.example.model.User;
import com.example.serveice.inser.FileService;
import com.example.util.Msg;
import com.example.util.ResultUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
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

    @Override
    public Msg getExcelOrder(String fileName, MultipartFile file) throws Exception {
        boolean notNull = false;
        List<User> userList = new ArrayList<>();
        if(!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")){
            return ResultUtil.error( 100,"上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if(fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream inputStream = file.getInputStream();
        Workbook wb = null;
        if(isExcel2003){
            wb = new HSSFWorkbook(inputStream);
        }else
            wb = new XSSFWorkbook(inputStream);
        //获取第一个sheet页
        Sheet sheet = wb.getSheetAt(0);
        if(sheet !=null) {
            User user;
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }
                user = new User();
                row.getCell(0).setCellType(CellType.STRING);
                String acount = row.getCell(0).getStringCellValue();
                if (acount == null || acount.equals("")) {
                    return ResultUtil.error(100,"导入失败，请检查第\"+r+1+\"行的账号是否未填写");
                }

                row.getCell(1).setCellType(CellType.STRING);
                String nickName = row.getCell(1).getStringCellValue();
                if (nickName == null || nickName.equals("")) {
                    return ResultUtil.error(100,"\"导入失败，请检查第\\\"+r+1+\\\"行的昵称是否未填写\"");
                }

                row.getCell(2).setCellType(CellType.STRING);
                String age = row.getCell(2).getStringCellValue();
                if (age == null || age == "") {
                    return ResultUtil.error(100,"导入失败，请检查第\"+r+1+\"行的年龄是否未填写");
                }

                row.getCell(3).setCellType(CellType.STRING);
                String emil = row.getCell(3).getStringCellValue();
                if (emil == null || emil == "") {
                    return ResultUtil.error(100,"导入失败，请检查第\"+r+1+\"行的邮箱是否未填写");
                }

                row.getCell(4).setCellType(CellType.STRING);
                String ssex = row.getCell(4).getStringCellValue();
                if (ssex == null || ssex == "") {
                    return ResultUtil.error(100,"导入失败，请检查第\"+r+1+\"行的性别是否未填写");
                }

                row.getCell(5).setCellType(CellType.STRING);
                String phone = row.getCell(5).getStringCellValue();
                if (phone == null || phone == "") {
                    return ResultUtil.error(100,"导入失败，请检查第\"+r+1+\"行的电话是否未填写");
                }

                user.setAcount(acount);
                user.setNickName(nickName);
                user.setSsex(ssex);
                user.setPhone(phone);
                user.setEmil(emil);
                user.setAge(age);
                fileMapper.addUser(user);
                System.out.print("插入用户成功");
            }
            return ResultUtil.success("导入用户成功");
        }
            return ResultUtil.error(100,"sheet为空");
    }
}
