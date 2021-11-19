package com.harry.boostrap.startup.analyze.excel;

import lombok.Data;

@Data
public class ExportData {
    /**
     * 头键
     */
    private String key;
    /**
     * 头标题
     */
    private Object value;
    /**
     * 批注
     */
    private String comment;

    private ColorType colorType;

    public ExportData(String key, String value, String comment) {
        this.key = key;
        this.value = value;
        this.comment = comment;
    }

    public ExportData(Object value, ColorType colorType) {
        this.value = value;
        this.colorType = colorType;
    }

    public ExportData(Object value) {
        this.value = value;
    }
}