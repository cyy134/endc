package com.example.util;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

public class ExcelUtil {


    /**
     * 单文件上传
     *
     * @param file
     * @return
     */
    public static String uploadOne(MultipartFile file) {
        if (file.isEmpty()) {
            return "false";
        }
        String filename = file.getOriginalFilename();
        int size = (int) file.getSize();

        String path = "D:/WUYU";
        File dest = new File(path + "/" + filename);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return "true";
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }

    /**
     * 下载文件
     *
     * @param response
     */
    public static void download(HttpServletResponse response) {
        String pathFile = "D:\\WUYU";
        //要下载的文件名字
        String fileName = "user.xls";
        //通过文件的保存文件夹路径加上文件的名字来获得文件
        File file = new File(pathFile, fileName);
        //当文件存在
        if (file.exists()) {
            //首先设置响应的内容格式是force-download，那么你一旦点击下载按钮就会自动下载文件了
            response.setContentType("application/force-download");
            //通过设置头信息给文件命名，也即是，在前端，文件流被接受完还原成原文件的时候会以你传递的文件名来命名
            response.addHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
            //进行读写操作
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                //从源文件中读
                int i = bis.read(buffer);
                while (i != -1) {
                    //写到response的输出流中
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //善后工作，关闭各种流
                try {
                    if (bis != null) {
                        bis.close();
                    }
                    if (fis != null) {
                        fis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 导入excel文件实现批量新增
     *
     * @param fileName
     * @param file
     * @param sheetIndex
     * @return
     * @throws Exception
     */
    public static Sheet importExcel(String fileName, MultipartFile file, int sheetIndex) throws Exception {

        //判断版本
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        //获取输入流
        InputStream inputStream = file.getInputStream();
        Workbook wb = null;
        //获取工作铺
        if (isExcel2003) {
            wb = new HSSFWorkbook(inputStream);
        } else
            wb = new XSSFWorkbook(inputStream);
        //获取第一个sheet页
        Sheet sheet = wb.getSheetAt(sheetIndex);
        return sheet;
    }

    /**
     * 以excel格式导出
     *
     * @param response
     * @param list
     * @param sheetName
     * @param fileName
     * @param columnWidth
     */
    public static void exportExcel(HttpServletResponse response, List<List<String>> list, String sheetName, String fileName, int columnWidth) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);
        //设置单元格列宽
        sheet.setDefaultColumnWidth(columnWidth);

        try {
            int rows = list.size();
            int columns = list.get(0).size();
            for (int r = 0; r < rows; r++) {
                //创建第r行
                HSSFRow row = sheet.createRow(r);
                for (int c = 0; c < columns; c++) {
                    //创建第r行的第c个单元格
                    HSSFCell cell = row.createCell(c);
                    HSSFRichTextString text = new HSSFRichTextString(list.get(r).get(c));
                    cell.setCellValue(text);
                }
            }
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            response.flushBuffer();
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static Boolean uploadFiles(HttpServletRequest request) {
        boolean flag = false;
        List<MultipartFile> multipartFiles = ((MultipartHttpServletRequest) request).getFiles("fileName");
        if (multipartFiles.isEmpty()) {
            return flag;
        }
        String path = "D:/WUYU";

        for (MultipartFile file : multipartFiles) {
            String filename = file.getOriginalFilename();
            int size = (int) file.getSize();
            if (file.isEmpty()) {
                return flag;
            } else {
                File dest = new File(path + "/" + filename);
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                try {
                    file.transferTo(dest);
                } catch (Exception e) {
                    e.printStackTrace();
                    return flag;
                }
            }
        }
        return true;
    }
}
