package com.example.serveice.imp;

import com.example.dao.FileMapper;
import com.example.dao.UserMapper;
import com.example.model.User;
import com.example.serveice.inser.FileService;
import com.example.util.ExcelUtil;
import com.example.util.Msg;
import com.example.util.ResultUtil;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

//上传excel表数据到数据库（没有验证）
@Service
public class FielServiceImp implements FileService {

    @Autowired
    FileMapper fileMapper;

    @Autowired
    UserMapper userMapper;

    /**
     * 下载模板文件
     * @param response
     */
    @Override
    public void download(HttpServletResponse response){
        try {
            ExcelUtil.download(response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 导入excel(简易版)
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    public boolean getExcel(MultipartFile file) throws Exception {
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

//            fileMapper.addUser(user);
        }
        return true;
    }

    /**
     * 导入excel(完善版)
     * @param fileName
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    public Msg getExcelOrder(String fileName, MultipartFile file) throws Exception {
        List<User> userList = new ArrayList<>();
        //正则表达式判断文件格式
        if(!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")){
            return ResultUtil.error( 100,"上传文件格式不正确");
        }

        String acount="";
        String emil="";
        String nickName="";
        String age="";
        String ssex="";
        String phone="";
        Sheet sheet = ExcelUtil.importExcel(fileName,file,0);
        if(sheet !=null) {
            User user;
            //从第二行数据开始，第一行是标题
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                //此行为null跳出本次循环
                if (row == null) {
                    continue;
                }
                user = new User();

                //进行单元格校验
                if(row.getCell(0) != null) {
                    row.getCell(0).setCellType(CellType.STRING);
                    acount = row.getCell(0).getStringCellValue();
                    if (acount == null || acount.equals("")) {
                        return ResultUtil.error(100, "导入失败，请检查第\"+r+1+\"行的账号是否未填写");
                    }
                }

                if(row.getCell(1) != null) {
                    row.getCell(1).setCellType(CellType.STRING);
                    nickName = row.getCell(1).getStringCellValue();
                    if (nickName == null || nickName.equals("")) {
                        return ResultUtil.error(100, "\"导入失败，请检查第\\\"+r+1+\\\"行的昵称是否未填写\"");
                    }
                }

                if(row.getCell(2) != null) {
                    row.getCell(2).setCellType(CellType.STRING);
                    age = row.getCell(2).getStringCellValue();
                    if (age == null || age.equals("")) {
                        return ResultUtil.error(100, "导入失败，请检查第\"+r+1+\"行的年龄是否未填写");
                    }
                }


                if(row.getCell(4)!=null) {
                    row.getCell(4).setCellType(CellType.STRING);
                    emil = row.getCell(4).getStringCellValue();
                    if (emil == null || emil.equals("")) {
                        return ResultUtil.error(100, "导入失败，请检查第\"+r+1+\"行的邮箱是否未填写");
                    }
                }

                if(row.getCell(5) != null) {
                    row.getCell(5).setCellType(CellType.STRING);
                    ssex = row.getCell(5).getStringCellValue();
                    if (ssex == null || ssex.equals("")) {
                        return ResultUtil.error(100, "导入失败，请检查第\"+r+1+\"行的性别是否未填写");
                    }
                }

                if(row.getCell(3) != null) {
                    row.getCell(3).setCellType(CellType.STRING);
                    phone = row.getCell(3).getStringCellValue();
                    if (phone == null || phone.equals("")) {
                        return ResultUtil.error(100, "导入失败，请检查第\"+r+1+\"行的电话是否未填写");
                    }
                }

                user.setAcount(acount);
                user.setNickName(nickName);
                user.setSsex(ssex);
                user.setPhone(phone);
                user.setEmil(emil);
                user.setAge(age);
                userList.add(user);
                //判断新添加的账号是否已经存在。
                User exisUser = userMapper.getUserInfoByAcount(acount);
                if(exisUser.getAcount().equals(acount)){
                    return ResultUtil.error(100,"导入失败，第\"+r+1+\"行的账号已经存在，" +
                            "不可重复添加，若要覆盖请先删除重复用户");
                }
                fileMapper.addUser(user);
                System.out.print("插入用户成功");
            }
//            fileMapper.addUser(userList);
            return ResultUtil.success("导入用户成功");
        }
            return ResultUtil.error(100,"sheet为空");
    }


    /**
     * 以excel表方式导出
     * @param response
     */
    @Override
    public void exportExcel(HttpServletResponse response) {
        try {
            List<User> userList = userMapper.getAllUser();
            List<List<String>> lists = new ArrayList<>();
            List<String> listhead = new ArrayList<>();
            listhead.add("账号");
            listhead.add("昵称");
            listhead.add("年龄");
            listhead.add("创建时间");
            listhead.add("电话");
            listhead.add("邮箱");
            listhead.add("性别");
            lists.add(listhead);
            for (User item : userList) {
                List<String> listString = new ArrayList<>();
                listString.add(item.getAcount());
                listString.add(item.getNickName());
                listString.add(item.getAge());
                listString.add(item.getCreatetime());
                listString.add(item.getPhone());
                listString.add(item.getEmil());
                listString.add(item.getSsex());
                lists.add(listString);
            }
            ExcelUtil.exportExcel(response, lists, "用户表", "user.xls", 10);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
