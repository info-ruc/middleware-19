package com.printer.core.utils;

import com.printer.core.common.log.PLog;
import com.printer.core.constant.Config;

import java.io.File;
import java.util.Date;

import com.printer.core.entity.FileOrder;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
    /**
     * 处理上传的文件，转化为pdf后存放于指定路径
     * @param file 用户上传的文件对象
     * @throws Exception 转换pdf时报异常
     * @return File对象
     */
    public static FileOrder processFile(MultipartFile file) throws Exception {

        // 文件存储目录
        // TODO: 1.注意文件路径 需要修改适应windows 系统
        // TODO: 2.需要弄清楚tomcat的暂存路径是什么，接收到的文件是否先保存到暂存路径？
        // TODO    如果是这样，则转化为pdf时直接从暂存路径到保存路径并删除临时文件，就不需要调用transferTo做传存了

        // 文件名
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf(".")+1);

        // 判断后缀名是否合法
        if (!Config.SUFFIX.contains(fileType)) {
            // TODO 此处是否应该抛出异常？
            return null;
        }
        // 生成文件保存的随机目录
        String filePath = Config.UPLOAD_DIR + RandomUtil.getRandomDir(Config.NUM_DIR);
        File destDir = new File(filePath);

        // 如果目录不存在则创建
        if(!destDir.exists()){
            destDir.mkdirs();
        }

        String noSuffixPath = filePath + Config.DELIMITER + RandomUtil.getRandomFileName();
        // 文件转换前的路径
        String srcPath = noSuffixPath + "." + fileType;
        // 转换成pdf后的路径
        String destPath = noSuffixPath + ".pdf";


        File srcFile = new File(srcPath);      // 文件目标路径
        int pageNumber = 0;

        try {


            if (fileType.equals("doc") || fileType.equals("docx")) {
                // word转pdf
                PdfUtil.word2pdf(file, destPath);

                // 删除原word文件(一定是转换成功了才能删除，pdf文件是不需要转换的，故不用删除)
                // FileUtil.deleteFile(srcPath);
            } else {
                file.transferTo(srcFile);             // 转存文件到本地
            }

            // 计算页数
            pageNumber = PdfUtil.getPageNumber(destPath);



        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return new FileOrder(fileName, destPath, 1, pageNumber, 1,
                new Date(), 0);
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.isFile() && file.exists()) {
            try {
                file.delete();
            } catch (Exception e) {
                PLog.error("Remove file error");
            }
        }
    }


    public static void main(String[] args) {


    }
}
