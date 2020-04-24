package com.example.util;
import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ExcelUtil {

    //以excel格式导出
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
        }catch (Exception e) {
            e.printStackTrace();

        }

    }
}
