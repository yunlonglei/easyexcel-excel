package com.lei.importexcel.excelConfig;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.AbstractCellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author leiyunlong
 * @version 1.0
 * @since 2021/9/8 9:34 上午
 */
@Component
public class CustomCellWriteHandler extends AbstractCellWriteHandler {
    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
        // 设置行高测试
        int rowIndex = row.getRowNum();
        short height;
        if (rowIndex == 0) {
            height = 800;
        } else {
            height = 500;
        }
        row.setHeight(height);
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                                 List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        int rowIndex = cell.getRowIndex();
        int cellIndex = cell.getColumnIndex();

        // 自定义宽度处理
        if (rowIndex == 1) {
            int columnWidth = cell.getStringCellValue().getBytes().length;
            switch (cellIndex) {
                case 0:
                case 2:
                case 3:
                    columnWidth = 10;
                    break;
                case 1:
                    columnWidth = 25;
                    break;
                case 4:
                    columnWidth = 15;
                    break;
                case 5:
                    columnWidth = 50;
                    break;
                default:
                    break;
            }

            if (columnWidth > 255) {
                columnWidth = 255;
            }
            writeSheetHolder.getSheet().setColumnWidth(cellIndex, columnWidth * 256);
        }

        // 自定义样式处理
        if (rowIndex >= 2) {
            if (cellIndex >= 3) {
                CellStyle cellStyle = cell.getSheet().getWorkbook().createCellStyle();
                cellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.index);
                cellStyle.setFillBackgroundColor(IndexedColors.WHITE.index);
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setBorderBottom(BorderStyle.THIN);

                DataFormat dataFormat = cell.getSheet().getWorkbook().createDataFormat();
                cellStyle.setDataFormat(dataFormat.getFormat("0.00"));
                cell.setCellStyle(cellStyle);
                System.out.println("set cell style");
            }
        }
    }
}
