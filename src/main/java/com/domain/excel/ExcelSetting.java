package com.domain.excel;

import cn.afterturn.easypoi.excel.export.styler.AbstractExcelExportStyler;
import cn.afterturn.easypoi.excel.export.styler.IExcelExportStyler;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * create by mjl on2018/12/18
 */
public class ExcelSetting  extends AbstractExcelExportStyler implements IExcelExportStyler {
    public ExcelSetting(Workbook workbook) {
        super.createStyles(workbook);
    }

    public CellStyle getTitleStyle(short color) {
        CellStyle titleStyle = this.workbook.createCellStyle();
        Font font = this.workbook.createFont();
        font.setBold(true); //字体加粗
        font.setFontName("Arial");
        titleStyle.setFont(font);
        titleStyle.setAlignment((short)2);    //居中
        titleStyle.setVerticalAlignment((short)1);   //垂直居中
        titleStyle.setWrapText(true);
        return titleStyle;
    }

    public CellStyle stringSeptailStyle(Workbook workbook, boolean isWarp) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment((short)2);
        style.setVerticalAlignment((short)1);
        style.setDataFormat(STRING_FORMAT);
        if (isWarp) {
            style.setWrapText(true);
        }

        return style;
    }

    public CellStyle getHeaderStyle(short color) {
        CellStyle titleStyle = this.workbook.createCellStyle();
        Font font = this.workbook.createFont();
        font.setFontHeightInPoints((short)12);
        titleStyle.setFont(font);
        titleStyle.setAlignment((short)2);
        titleStyle.setVerticalAlignment((short)1);
        return titleStyle;
    }

    public CellStyle stringNoneStyle(Workbook workbook, boolean isWarp) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment((short)2);
        style.setVerticalAlignment((short)1);
        style.setDataFormat(STRING_FORMAT);
        if (isWarp) {
            style.setWrapText(true);
        }

        return style;
    }
}
