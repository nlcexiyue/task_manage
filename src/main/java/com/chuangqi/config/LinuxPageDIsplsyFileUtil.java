package com.chuangqi.config;

import org.apache.poi.util.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Author  : gblfy
 * @Date    : 2019/11/01  11:20
 * @describe: 文档在线预览
 *
 * 服务器环境：Linux环境
 * 现支持文档类型: Excel  word  ppt pdf
 */
/**
 * @Author  : gblfy
 * @Date    : 2019/11/01  11:20
 * @describe: 文档在线预览
 *
 * 服务器环境：Linux环境
 * 现支持文档类型: Excel  word  ppt pdf
 */
public class LinuxPageDIsplsyFileUtil {

    private static LinuxPageDIsplsyFileUtil linuxPageDIsplsyFileUtil;

    public static synchronized LinuxPageDIsplsyFileUtil getSwitchUtil() {
        if (linuxPageDIsplsyFileUtil == null) {
            linuxPageDIsplsyFileUtil = new LinuxPageDIsplsyFileUtil();
        }
        return linuxPageDIsplsyFileUtil;
    }

    /**
     * 文档在线预览
     *
     * @param response
     * @param fileStoragePath  文件存储路径 (前段获取文件存储路径返给后台)
     * @param beforeConversion 文件名(必须带文件后缀名,这里指的就是文件全名称)
     * @throws Exception
     */
    public void conversionFile(HttpServletResponse response, String fileStoragePath, String beforeConversion) throws Exception {
        //文件存储路径
        //fileStoragePath ="/app/";
        //转换前的文件名
        //beforeConversion ="20191009133209lis_chgrpt.docx";
        String fileNamePath = fileStoragePath + beforeConversion;
        File file = new File(fileNamePath);
        if (!file.exists()) {
            System.err.println("库存中没有指定文件。。。。");
            return;
        }
        //获取到文件名
        String interceptFileName = beforeConversion.substring(0, beforeConversion.lastIndexOf("."));
        //截取文件后缀名
        String fileNameSuffix = beforeConversion.substring(beforeConversion.lastIndexOf(".") + 1);
        String command = null;
        if("pdf".equals(fileNameSuffix)){
            /**
             * 在线预览方法
             */
            openPdf(response, fileStoragePath + interceptFileName + ".pdf");
        }else if("doc".equals(fileNameSuffix)||"docx".equals(fileNameSuffix)||"xls".equals(fileNameSuffix)||"xlsx".equals(fileNameSuffix)
                ||"ppt".equals(fileNameSuffix)||"pptx".equals(fileNameSuffix)) {
            //文件格式转换命令 unoconv插件实现
            command = "/usr/bin/unoconv -f pdf " + fileNamePath;
            //格式转换+在线预览
            formatConverAndPreview(command,response,fileStoragePath,interceptFileName);
        // }else if("docx".equals(fileNameSuffix)) {
        //     command = "/usr/bin/unoconv -f pdf " + fileNamePath;
        //     formatConverAndPreview(command,response,fileStoragePath,interceptFileName);
        // }else if("xls".equals(fileNameSuffix)) {
        //     command = "/usr/bin/unoconv -f pdf " + fileNamePath;
        //     formatConverAndPreview(command,response,fileStoragePath,interceptFileName);
        // }else if("xlsx".equals(fileNameSuffix)) {
        //     command = "/usr/bin/unoconv -f pdf " + fileNamePath;
        //     formatConverAndPreview(command,response,fileStoragePath,interceptFileName);
        // }else if("ppt".equals(fileNameSuffix)) {
        //     command = "/usr/bin/unoconv -f pdf " + fileNamePath;
        //     formatConverAndPreview(command,response,fileStoragePath,interceptFileName);
            // }else if("pptx".equals(fileNameSuffix)) {
        //     command = "/usr/bin/unoconv -f pdf " + fileNamePath;
        //     formatConverAndPreview(command,response,fileStoragePath,interceptFileName);
        }else{
            System.err.println("暂不支持该类型文件在线预览！！！");
            return;
        }
    }

    /**
     * 格式转换+在线预览 方法
     *
     * @param command            文件格式转换命令         例：/usr/bin/unoconv -f pdf  /app/1.pptx
     * @param response           http响应网页,实现在线预览
     * @param fileStoragePath    准备文件存放路径         例：/app/
     * @param interceptFileName  文件名                  例: 1.pptx
     * @throws Exception
     */
    public void formatConverAndPreview(String command,
                                       HttpServletResponse response,
                                       String fileStoragePath,
                                       String interceptFileName)throws Exception{
        /**
         * 格式转换方法
         */
        //String temp ="/usr/bin/unoconv -f pdf " + command;
        executeCommand(command);
        /**
         * 在线预览方法
         */
        openPdf(response, fileStoragePath + interceptFileName + ".pdf");
    }

    /**
     * 在线预览方法
     * 把转换后的pdf文件在网页上进行预览
     *
     * @param response  http响应
     * @param previewFile  文件的決定路径  例：/app/20191009133209_chgrpt.pdf
     * @throws Exception  格式转换过程中的异常
     */
    private static void openPdf(HttpServletResponse response, String previewFile) throws Exception {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        //String path ="/app/20191009133209_chgrpt.pdf";
        inputStream = new FileInputStream(previewFile);
        //响应文件的类型
        response.setContentType("application/pdf");
        outputStream = response.getOutputStream();
        int a = 0;
        byte[] b = new byte[1024];
        while ((a = inputStream.read(b)) != -1) {
            outputStream.write(b, 0, a);
        }
        if (outputStream != null) {
            outputStream.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
    }

    /**
     * 格式转换方法
     * <p>
     * 統一把文件转换成pdf文件
     *
     * @param command 文件格式转换命令   例：/usr/bin/unoconv -f pdf  /app/1.pptx
     * @throws Exception   格式转换过程中的异常
     */
    private static void executeCommand(String command) throws Exception {

        StringBuffer output = new StringBuffer();
        Process process;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        try {
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
            inputStreamReader = new InputStreamReader(process.getInputStream(), "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
            //p.destroy();//这个一般不需要
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(inputStreamReader);
        }
    }
}
