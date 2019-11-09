package com.printer.core.constant;

import java.util.Arrays;
import java.util.List;

public class Config {
    // 文件上传存放的根目录(注意最后要加"/")
    public static final String UPLOAD_DIR = "/Users/wq/pdfFile/";

    // print管理程序提供的接口
    public static final String PRINTER_URL = "http://localhost:8080/api/printMultiPDFs";

    // 合法的文件后缀集合
    public static final List<String> SUFFIX = Arrays.asList("doc", "docx", "pdf");

    // 文件系统分隔符
    public static final String DELIMITER = "/";

    // 上传文件根目录下的子目录数
    public static final int NUM_DIR = 32;
}
