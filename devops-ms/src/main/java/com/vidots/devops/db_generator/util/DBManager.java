package com.vidots.devops.db_generator.util;

import com.vidots.utils.LogAPI;
import com.vidots.utils.StringUtils;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DBManager {

    private static final String DRIVER = MySQL.driver.getValue();
    private static Connection conn;

    static {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(MySQL.host.getValue(), MySQL.username.getValue(), MySQL.password.getValue());
        } catch (ClassNotFoundException e) {
            LogAPI.err("请下载Maven安装包");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<FieldInfo> getColumns() {
        if (conn == null) {
            LogAPI.err("数据库连接失败，请确保配置的信息正确");
            System.exit(-1);
        }
        List<FieldInfo> result = new ArrayList<>();
        PreparedStatement pst;
        String sql = "SELECT * FROM " + MySQL.table.getValue();
        LogAPI.debug("获取操作的数据表，执行查询：" + sql);
        try {
            pst = conn.prepareStatement(sql);
            ResultSetMetaData rsmd = pst.getMetaData();
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                String colName = rsmd.getColumnName(i + 1);
                String colType = rsmd.getColumnTypeName(i + 1);
                LogAPI.debug("字段名：" + colName + " 字段类型：" + colType);
                result.add(new FieldInfo(colName, colType, rsmd.isAutoIncrement(i + 1), rsmd.getPrecision(i + 1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 调用此方法，大约在180万左右数据的时候就会发生OOM
    public static void insertData_oom() throws ArithmeticException, SQLException {
        List<FieldInfo> filteredColumns = DBManager.getColumns().stream().filter(column -> !column.isAutoIncr()).collect(Collectors.toList());
        if (conn == null) {
            LogAPI.err("数据库连接失败，请确保配置的信息正确");
            System.exit(-1);
        }
        LogAPI.debug("--------开始插入数据 -------");
        String numStr = MySQL.insertNum.getValue();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        int insertNum = Integer.parseInt(numStr);
        int perCount = Integer.parseInt(MySQL.insertPer.getValue());
        String[] valueStr = new String[perCount];
        long startTime = System.currentTimeMillis();
        String fieldStr = filteredColumns.stream().map(FieldInfo::getColName).collect(Collectors.joining(","));
        PreparedStatement pst;
        for (int i = 0; i < insertNum / perCount; i++) {
            for (int j = 0; j < perCount; j++) {
                valueStr[j] = filteredColumns.stream().map(column -> "'" + column.randomValue() + "'").collect(Collectors.joining(","));
            }
            String fieldValues = String.join("),(", valueStr);
            String sql = "INSERT INTO " + MySQL.table.getValue() + "(" + fieldStr + ") VALUES (" + fieldValues + ");";

            LogAPI.debug("执行插入的语句：" + sql.substring(0, StringUtils.indexOfNth(1, sql.toString(), "),")) + "),.......");

            try {
                pst = conn.prepareStatement(sql);
                pst.executeUpdate();
                LogAPI.println("当前进度：" + (i + 1) * perCount + "/" + insertNum  + " 当前时间：" + formatter.format(new Date()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        LogAPI.println("插入" + insertNum + "条数据，花费的时间：" + (endTime - startTime) / 1000 / 60 + "分钟");
        conn.close();
    }


    public static void insertData() throws ArithmeticException, SQLException {
        List<FieldInfo> filteredColumns = DBManager.getColumns().stream().filter(column -> !column.isAutoIncr()).collect(Collectors.toList());
        if (conn == null) {
            LogAPI.err("数据库连接失败，请确保配置的信息正确");
            System.exit(-1);
        }
        LogAPI.debug("--------开始插入数据 -------");
        String numStr = MySQL.insertNum.getValue();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        int insertNum = Integer.parseInt(numStr);

        Statement statement = conn.createStatement();
        int perCount = Integer.parseInt(MySQL.insertPer.getValue());
        String[] valueStr = new String[perCount];
        long startTime = System.currentTimeMillis();
        String fieldStr = filteredColumns.stream().map(FieldInfo::getColName).collect(Collectors.joining(","));
        for (int i = 0; i < insertNum / perCount; i++) {
            for (int j = 0; j < perCount; j++) {
                valueStr[j] = filteredColumns.stream().map(column -> "'" + column.randomValue() + "'").collect(Collectors.joining(","));
            }
            String fieldValues = String.join("),(", valueStr);
            String sql = "INSERT INTO " + MySQL.table.getValue() + "(" + fieldStr + ") VALUES (" + fieldValues + ");";

            LogAPI.debug("执行插入的语句：" + sql.substring(0, StringUtils.indexOfNth(1, sql.toString(), "),")) + "),.......");

            try {
                statement.execute(sql);
                LogAPI.println("当前进度：" + (i + 1) * perCount + "/" + insertNum  + " 当前时间：" + formatter.format(new Date()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        LogAPI.println("插入" + insertNum + "条数据，花费的时间：" + (endTime - startTime) / 1000 / 60 + "分钟");
        conn.close();
    }


    public static void insertData_prepare() throws ArithmeticException, SQLException {
        List<FieldInfo> filteredColumns = DBManager.getColumns().stream().filter(column -> !column.isAutoIncr()).collect(Collectors.toList());
        if (conn == null) {
            LogAPI.err("数据库连接失败，请确保配置的信息正确");
            System.exit(-1);
        }
        LogAPI.debug("--------开始插入数据 -------");
        String numStr = MySQL.insertNum.getValue();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        int insertNum = Integer.parseInt(numStr);

        int perCount = Integer.parseInt(MySQL.insertPer.getValue());
        long startTime = System.currentTimeMillis();
        String fieldStr = filteredColumns.stream().map(FieldInfo::getColName).collect(Collectors.joining(","));
        String phStr = filteredColumns.stream().map(column -> "?").collect(Collectors.joining(","));
        String executeSql = "INSERT INTO " + MySQL.table.getValue() + "(" + fieldStr + ") VALUES(" + phStr + ");";
        PreparedStatement statement = conn.prepareStatement(executeSql);
        LogAPI.println(executeSql);

        for (int i = 0; i < insertNum / perCount; i++) {
            for (int j = 0; j < perCount; j++) {
                for(int k = 0; k < filteredColumns.size(); k++) {
                    statement.setString(k + 1, filteredColumns.get(k).randomValue());
                }
                statement.addBatch();
            }
            try {
                statement.executeBatch();
                LogAPI.println("当前进度：" + (i + 1) * perCount + "/" + insertNum  + " 当前时间：" + formatter.format(new Date()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        LogAPI.println("插入" + insertNum + "条数据，花费的时间：" + (endTime - startTime) / 1000 / 60 + "分钟");
        conn.close();
    }


}
