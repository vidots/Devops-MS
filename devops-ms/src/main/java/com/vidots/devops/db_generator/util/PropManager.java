package com.vidots.devops.db_generator.util;

import java.io.*;
import java.util.Properties;

public class PropManager {

    private static final Properties pro;

    private PropManager(){}

    static {
        String fsName = "db_generator/config.properties";
        pro = new Properties();
        String resPath = PropManager.class.getClassLoader().getResource(fsName).getPath();
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(resPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            pro.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return pro.getProperty(key);
    }
}























