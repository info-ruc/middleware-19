package com.printer.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class RandomUtil {
    /**
     * 生成一个指定范围的随机整数
     * @param start 随机数的起始值
     * @param end 随机数的终止值，小于end
     * @return 返回int类型随机数
     */
    private static int getRandom(int start, int end) {
        Random random = new Random();
        return random.nextInt(end-start) + start;
    }

    /**
     * 生成随机目录名称，为0~bound之间的随机数字符串
     * @param bound 随机数范围
     * @return String类型目录名，不足2位以0补足
     */
    public static String getRandomDir(int bound) {
        int num = getRandom(0, bound);
        return String.format("%02d", num);
    }

    /**
     * 生成随机文件名
     * (并发时毫秒时间相同，后期最好再加上文件名称的hash)
     * @return 16位随机字符串组成的文件名
     */
    public static String getRandomFileName() {

        long t = System.currentTimeMillis();    // 获取系统毫秒时间
        int num = getRandom(100, 1000);         // 生成3位随机数
        return t + "" + num;
    }

    public static void main(String[] args) {
        long t = System.currentTimeMillis();
        System.out.println(t);

        // uuid 耗时10ms
        System.out.println(UUID.randomUUID().toString());

        // 这种生成时间随机数的耗时100ms，别用了
        // SimpleDateFormat spd = new SimpleDateFormat("yyyyMMddkkmmss");
        // Date now = new Date();
        // String t2 = spd.format(now);
        // System.out.println(now);

        long tt = System.currentTimeMillis();
        System.out.println(tt);

        // 耗时1ms以内
        System.out.println(getRandomFileName());
        System.out.println(getRandomFileName());

        String fileName = "zzz.zz.zz";
        System.out.println(fileName.lastIndexOf("."));
    }
}

