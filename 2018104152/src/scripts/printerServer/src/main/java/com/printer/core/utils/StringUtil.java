package com.printer.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class StringUtil {

    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static boolean isEmpty(String str) {
        return  (str == null || str.length() <= 0);
    }

    /**
     * 将其它类型的数据转换成字符串
     */
    public static String paraseString(Object object){

        if(object == null){
            return "";
        }
        if(object instanceof String){
            return String.valueOf(object);
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
             e.printStackTrace();
        }
        return "";
    }
}
