package com.vidots.utils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class GeneratorAPI {
    private static final List<String> INTS = Arrays.asList( "BOOL", "TINYINT", "SMALLINT", "MEDIUMINT", "INT", "INTEGER", "BIGINT", "LONG");
    private static final List<String> TEXTS = Arrays.asList("CHAR", "VARCHAR", "TINYTEXT", "TEXT", "MEDIUMTEXT", "LONGTEXT");
    private static final Map<String, Integer> INT_WIDTH = new HashMap<String, Integer>() {{
        put("BOOL", 8);
        put("TINYINT", 8);
        put("SMALLINT", 16);
        put("MEDIUMINT", 24);
        put("INT", 24); // 从INT开始，随机数设定更小的边界
        put("INTEGER", 24);
        put("BIGINT", 24);
        put("LONG", 24);
    }};

    public static String generate(String colType, Integer size) {
        Random machine = new Random();
        if ("BIT".equals(colType)) {
            return String.valueOf(machine.nextInt(2));
        }
        if (INTS.contains(colType)) {
//            int maxValue = 1 << INT_WIDTH.get(colType);
//            return String.valueOf(machine.nextInt(maxValue) - maxValue / 2);
            return String.valueOf(machine.nextInt(100)); // 根据字段类型生成的随机数往往脱离实际情况
        }
        if (colType.contains("UNSIGNED")) {
//            String filtered = colType.replace("UNSIGNED", "");
//            int maxValue = 1 << INT_WIDTH.get(filtered);
//            return String.valueOf((machine.nextInt(maxValue)));
            return String.valueOf(machine.nextInt(100)); // 根据字段类型生成的随机数往往脱离实际情况
        }
        if (colType.equals("FLOAT")) {
            return String.valueOf(machine.nextFloat());
        }
        if (colType.equals("DOUBLE") || colType.equals("DECIMAL")) {
            return String.valueOf(machine.nextDouble());
        }
        if (TEXTS.contains(colType)) {
            Integer maxLen = size > 100 ? 100 : size;
            return generateSentence(maxLen);
        }

        if ("DATE".equals(colType)) {
            return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
        if ("TIME".equals(colType)) {
            return new SimpleDateFormat("HH:mm:ss").format(new Date());
        }
        if ("DATETIME".equals(colType)) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        }
        if ("TIMESTAMP".equals(colType)) {
            return String.valueOf(new Date().getTime());
        }
        return "";
    }

    private static String generateSentence(Integer len) {
        StringBuilder textBuilder = new StringBuilder();
        textBuilder.append("Our brain is our greatest asset in life, so it is a no brainer that " +
                "we should invest some time learning how to use it effectively. In this concise " +
                "and carefully crafted book, renowned professors Burger and Starbird demonstrate " +
                "their talent for making difficult concepts accessible. An average reader can peruse " +
                "this book in only a few hours, but for many people those will be the best hours ever " +
                "spent on a book. Highly recommended");
        List<String> list = Arrays.asList(textBuilder.toString().split(" "));
        Collections.shuffle(list);
        StringBuilder result = new StringBuilder();
        result.append(UUID.randomUUID().toString());
        result.append(String.join(" ", list));
        return result.substring(0, len);

    }
}
