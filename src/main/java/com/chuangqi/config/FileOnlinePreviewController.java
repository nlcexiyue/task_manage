package com.chuangqi.config;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author gblfy
 * @ClassNme FileController
 * @Description 文件在在线预览
 * @Date 2019/11/1 8:09
 * @version1.0
 */
@RestController
public class FileOnlinePreviewController {
    /**
     * 在线预览测试方法
     * 企业真实需求:
     * 文件的路径 文件名 都需要动态获取
     *
     * @param response  http响应网页来实现在线预览
     * @throws Exception
     */
    @RequestMapping("/viewPDF")
    public void reviewWord(HttpServletResponse response)throws Exception{
        LinuxPageDIsplsyFileUtil linuxPageDIsplsyFileUtil = new LinuxPageDIsplsyFileUtil();
        //文件存储路径
        String fileStoragePath ="C:\\Users\\cq\\Desktop\\doc\\";
        //转换前的文件名
        String beforeConversion ="docker_practice.pdf";
        /**
         * 文件格式转换+在线预览
         */
        linuxPageDIsplsyFileUtil.conversionFile(response,fileStoragePath,beforeConversion);
    }
    @RequestMapping("/viewPDF2")
    public void reviewWord2(HttpServletResponse response)throws Exception{
        LinuxPageDIsplsyFileUtil linuxPageDIsplsyFileUtil = new LinuxPageDIsplsyFileUtil();
        //文件存储路径
        String fileStoragePath ="C:\\Users\\cq\\Desktop\\doc\\";
        //转换前的文件名
        String beforeConversion = "ss.xlsx";
        /**
         * 文件格式转换+在线预览
         */
        linuxPageDIsplsyFileUtil.conversionFile(response,fileStoragePath,beforeConversion);
    }
}
