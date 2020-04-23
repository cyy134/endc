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

    @RequestMapping(value = "/excel",method = RequestMethod.POST)
    public Msg upload(MultipartFile file, Model model) throws Exception {

        boolean flag = fileService.getExcel(file);
        if(flag){
            return ResultUtil.success();
        }else
            return ResultUtil.error(100,"错误");
    }

}
