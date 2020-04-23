package com.example.controller;

import com.example.util.Msg;
import com.example.util.ResultUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@RequestMapping("/cyy/file")
@RestController
public class FileController {

    //文件上传
    @RequestMapping("/uplod")
    public Msg upload(@RequestParam("file")MultipartFile file){
        try{
            if(file.isEmpty()){
                return ResultUtil.success( "file is empty");
            }
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            System.out.print("此次上传的文件名是  "+fileName+"后缀为： "+suffixName);
            //设置文件存储路径为D盘
            String filePath = "D:\\imagesTest\\";
            String path = filePath + fileName;
            File dest = new File(path);
            if(!dest.getParentFile().exists()){
                dest.getParentFile().mkdirs();//新建文件夹
            }
            file.transferTo(dest);
            return ResultUtil.success("upload success");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtil.error(100,"upload failure");
    }


}
