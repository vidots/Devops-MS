package com.vidots.devops.db_generator.util;

import com.vidots.utils.GeneratorAPI;

public class FieldInfo {
    private String colName;
    private String colType;
    private Boolean isAutoIncr;
    private Integer size;

    public FieldInfo(String colName, String colType, Boolean isAutoIncr, Integer size) {
        this.colName = colName;
        this.colType = colType;
        this.isAutoIncr = isAutoIncr;
        this.size = size;
    }

    public String getColName() {
        return colName;
    }

    public String getColType() {
        return colType;
    }

    public Boolean isAutoIncr() {
        return isAutoIncr;
    }

    public Integer getSize() {
        return size;
    }

    public String randomValue() {
        String col_type = colType.replace(" ", "");
        return GeneratorAPI.generate(col_type, size);
    }
}
