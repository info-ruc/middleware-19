package com.printer.core.utils;

public class MessageUtil {
    public static String getMessage(String message) {
        // message 为 "success" 表示成功； 为其它字符串表示异常，内容为错误信息
        return String.format("{\"message\":\"%s\"}", message);
    }
}
