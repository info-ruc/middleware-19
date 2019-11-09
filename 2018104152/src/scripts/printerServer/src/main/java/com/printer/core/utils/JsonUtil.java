package com.printer.core.utils;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class JsonUtil {
    /*  弃用，现使用fastjason
    public static<T> String marshal(T obj) {
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
    */

    public static String marshal(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     *对象转Json字符串
     */
    public static String toJSONString(Object object, SerializerFeature... features) {
        return JSON.toJSONString(object, features);
    }

    /**
     * 字符串转对象
     */
    public static <T> T parseObject(String text, Class<T> clazz, Feature... features) {
        return JSON.parseObject(text, clazz, features);
    }

    /**
     * 字符串转集合
     */
    public static <T> List<T> parseList(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

    /**
     * 私有的构造方法,禁止创建对象
     */
    private JsonUtil(){}

    public static void main(String[] args) {

    }
}
