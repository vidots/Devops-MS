package com.vidots.devops.db_generator.util;

public enum MySQL {
    driver("db_driver"),
    host("db_host"),
    username("db_username"),
    password("db_password"),
    table("db_table"),
    insertNum("db_insert_num"),
    insertPer("db_insert_per");

    private final String key;

    MySQL(String key) {
        this.key = key;
    }

    public String getValue() {
        return PropManager.get(this.key);
    }
}
