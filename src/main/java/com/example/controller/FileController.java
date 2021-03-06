package com.example.controller;

import com.example.serveice.inser.FileService;
import com.example.util.Msg;
import com.example.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/cyy/file")
public class FileController {

    @Autowired
    FileService fileService;

    @RequestMapping(value = "/uploadfiles",method = RequestMethod.POST)
    public Msg uploadFiles(HttpServletRequest request){
        boolean flag = fileService.uploadFiles(request);
        if (flag){
            return ResultUtil.success(flag);
        }else
            return ResultUtil.error(400,"false");
    }

    @RequestMapping(value = "/uploadone")
    public Msg uploadOne(MultipartFile file){
        try {
            return ResultUtil.success(fileService.uploadOne(file));
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(400,"系统异常");
        }
    }

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

    /**
     * 导入excel模板实现批量新增（简洁版）
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/excel",method = RequestMethod.POST)
    public Msg upload(MultipartFile file) throws Exception {

        boolean flag = fileService.getExcel(file);
        if(flag){
            return ResultUtil.success();
        }else
            return ResultUtil.error(100,"错误");
    }

    /**
     * 导入excel模板实现批量新增（完善版）
     * @param file
     * @return
     */
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
