package com.printer.core.utils;

// 使用jacob+office进行pdf的转换

import java.io.*;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.PdfSaveOptions;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.printer.core.common.log.PLog;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.web.multipart.MultipartFile;


public class PdfUtil {

    private static final int wdDoNotSaveChanges = 0;            // 不保存待定的更改。
    private static final int wdFormatPDF = 17;                  // word转PDF格式

    public static void word2pdf1(String srcPath, String destPath) throws Exception {
        long start = System.currentTimeMillis();
        ActiveXComponent app = null;
        Dispatch doc = null;
        try {
            app = new ActiveXComponent("Word.Application");
            app.setProperty("Visible", false);
            Dispatch docs = app.getProperty("Documents").toDispatch();
            System.out.println("" + srcPath);

            doc = Dispatch.call(docs, "Open", srcPath, false, true).toDispatch();

            System.out.println("" + destPath);

            // 如果目标文件已存在则删除
            File destFile = new File(destPath);
            if (destFile.exists()) {
                destFile.delete();
            }

            Dispatch.call(doc, "SaveAs", destPath, wdFormatPDF);


            long end = System.currentTimeMillis();
            PLog.info("" + (end - start) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            // 关闭文档
            Dispatch.call(doc, "Close", false);

            // 退出word
            if (app != null) {
                app.invoke("Quit", wdDoNotSaveChanges);
            }
        }

        // 目前测试来说，不释放jacob的线程好像也没有内存飙升的现象出现，后续需要压力测试
        // ComThread.Release();
    }

    public static void ppt2pdf(String srcFilePath, String destPdfPath) {

    }

    public static int getPageNumber(String path) throws Exception {
        // long start = System.currentTimeMillis();
        int page = 0;
        try {
            PDDocument pdDocument = PDDocument.load(new File(path));
            page = pdDocument.getNumberOfPages();
            // long end = System.currentTimeMillis();
            // PLog.info(end - start + " ms");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return page;
    }



    // 通过Aspose.words进行转换的此种方法对格式支持太差，故弃用
    private static boolean getLicense() {
        boolean result = false;
        try {
            // 凭证
            String licenseStr =
                    "<License>\n" +
                            "  <Data>\n" +
                            "    <Products>\n" +
                            "      <Product>Aspose.Total for Java</Product>\n" +
                            "      <Product>Aspose.Words for Java</Product>\n" +
                            "    </Products>\n" +
                            "    <EditionType>Enterprise</EditionType>\n" +
                            "    <SubscriptionExpiry>20991231</SubscriptionExpiry>\n" +
                            "    <LicenseExpiry>20991231</LicenseExpiry>\n" +
                            "    <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>\n" +
                            "  </Data>\n" +
                            "  <Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature>\n" +
                            "</License>";
            InputStream license = new ByteArrayInputStream(licenseStr.getBytes("UTF-8"));
            License asposeLic = new License();
            asposeLic.setLicense(license);
            result = true;
        } catch (Exception e) {
            PLog.error(e);
        }
        return result;
    }

    // 使用aspose.word 进行pdf转换
    public static void word2pdf(MultipartFile multipartFile , String srcFilePath) {
        FileOutputStream fileOS = null;
        // 验证License
        if (!getLicense()) {
            PLog.error("验证License失败！");
            return;
        }

        try {
            Document doc = new Document(multipartFile.getInputStream());
            fileOS = new FileOutputStream(new File(srcFilePath));
            // 保存转换的pdf文件
            PdfSaveOptions options = new PdfSaveOptions();
            options.setExportDocumentStructure(true);

            doc.save(fileOS, options);
            PLog.info("123");

        } catch (Exception e) {
            e.printStackTrace();
            PLog.error(e);
        } finally {
            try {
                if(fileOS != null){
                    fileOS.close();
                }
            } catch (IOException e) {
                PLog.error(e);
            }
        }
    }


    public static void main(String[] args) {
        //word2pdf("/Users/zyj/Desktop/接口文档.docx", "/Users/zyj/Desktop/ttttt.pdf");

        int page = 0;
        try {
            page = getPageNumber("/Users/zyj/Desktop/学习/机器学习/资料/Ng-MLY01-13.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(page);
    }
}
