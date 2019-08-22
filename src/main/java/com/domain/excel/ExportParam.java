package com.domain.excel;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * create by mjl on2018/12/18
 */
@Data
public class ExportParam   {
    private String title;
    private short titleHeight = 10;
    private String secondTitle;
    private short secondTitleHeight = 8;
    private String sheetName;
    private String[] exclusions;
    private boolean addIndex;
    private String indexName = "序号";
    private int freezeCol;
    private short color = 9;
    private short headerColor = 40;
    private ExcelType type;
    private Class<?> style;
    private double headerHeight;
    private boolean isCreateHeadRows;
    private boolean isDynamicData;
    private boolean isAppendGraph;
    private int maxNum;
    public short height;

    public ExportParam() {
        this.type = ExcelType.HSSF;
        this.style = ExcelSetting.class;
        this.headerHeight = 9.0D;
        this.isCreateHeadRows = true;
        this.isDynamicData = false;
        this.isAppendGraph = true;
        this.maxNum = 0;
        this.height = 0;
    }

    public ExportParam(String title, String sheetName) {
        this.type = ExcelType.HSSF;
        this.style = ExcelSetting.class;
        this.headerHeight = 9.0D;
        this.isCreateHeadRows = true;
        this.isDynamicData = false;
        this.isAppendGraph = true;
        this.maxNum = 0;
        this.height = 0;
        this.title = title;
        this.sheetName = sheetName;
    }

    public ExportParam(String title, String sheetName, ExcelType type) {
        this.type = ExcelType.HSSF;
        this.style = ExcelSetting.class;
        this.headerHeight = 9.0D;
        this.isCreateHeadRows = true;
        this.isDynamicData = false;
        this.isAppendGraph = true;
        this.maxNum = 0;
        this.height = 0;
        this.title = title;
        this.sheetName = sheetName;
        this.type = type;
    }

    public ExportParam(String title, String secondTitle, String sheetName) {
        this.type = ExcelType.HSSF;
        this.style = ExcelSetting.class;
        this.headerHeight = 9.0D;
        this.isCreateHeadRows = true;
        this.isDynamicData = false;
        this.isAppendGraph = true;
        this.maxNum = 0;
        this.height = 0;
        this.title = title;
        this.secondTitle = secondTitle;
        this.sheetName = sheetName;
    }


}
