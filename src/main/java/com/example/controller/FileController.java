package com.example.controller;

import com.example.serveice.inser.FileService;
import com.example.util.Msg;
import com.example.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/cyy/file")
public class FileController {

    @Autowired
    FileService fileService;

    /**
     * 下载模板
     * @param response
     */
    @RequestMapping(value = "/download")
    public void download(HttpServletResponse response){
        try {
            fileService.download(response);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //导入excel模板实现批量新增（简洁版）
    @RequestMapping(value = "/excel",method = RequestMethod.POST)
    public Msg upload(MultipartFile file) throws Exception {

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

    /**
     * excel表导出列表
     * @param response
     */
    @RequestMapping(value = "/exportExcel")
    public void exportExcel(HttpServletResponse response){
        try {
            fileService.exportExcel(response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
