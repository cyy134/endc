package com.example.controller;

import com.example.serveice.inser.FileService;
import com.example.util.Msg;
import com.example.util.ResultUtil;
import com.sun.tools.internal.ws.processor.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/cyy/file")
public class FileController {

    @Autowired
    FileService fileService;

    //导入excel模板是吸纳批量新增（简洁版）
    @RequestMapping(value = "/excel",method = RequestMethod.POST)
    public Msg upload(MultipartFile file, Model model) throws Exception {

        boolean flag = fileService.getExcel(file);
        if(flag){
            return ResultUtil.success();
        }else
            return ResultUtil.error(100,"错误");
    }

    //导入excel模板实现批量新增（完善版）
    @RequestMapping(value = "/fileImportIsExcel",method = RequestMethod.POST)
    public Msg fileImport(MultipartFile file){
        Msg result=null;
        String fileName = file.getOriginalFilename();
        try {
            result = fileService.getExcelOrder(fileName,file);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
