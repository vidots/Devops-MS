package com.vidots.devops.db_generator.util;

import com.vidots.utils.LogAPI;

import java.sql.SQLException;


public class MockData {

    public static void execute() {
        execute(true);
    }

    public static void execute(boolean debug) {
        LogAPI.setDebug(debug);
        try {
            DBManager.insertData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
