package com.vidots.devops.db_generator;

import com.vidots.devops.db_generator.util.MockData;

// todo ：处理字段的约束为primary key和unique key的情况

public class Main {
    public static void main(String[] args) {
        MockData.execute(false);
    }
}
