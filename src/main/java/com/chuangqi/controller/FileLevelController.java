package com.chuangqi.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.ftp.Ftp;
import cn.hutool.extra.ftp.FtpMode;
import com.alibaba.fastjson.JSONObject;
import com.chuangqi.tool.util.MultipartFileToFile;
import com.chuangqi.tool.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.jodconverter.DocumentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/1/14
 * \* Time: 14:59
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Api(tags = "文件层级管理", description = "文件层级管理接口详细信息的描述")
@Controller
@RequestMapping(value = "/filelevel")
public class FileLevelController {

    // 第一步：转换器直接注入
    @Autowired
    private DocumentConverter converter;
    @Resource
    private HttpServletResponse response;
    @Value("${FilePath}")
    private String FilePath;
    @Value("${tmp.root}")
    private String rootPath;
    @Value("${tmp.rootTemp}")
    private String rootTempPath;
    @Value("${nginx.url}")
    private String nginxUrl;
    //下载的文件保存的路径
    String loadPath = "C:\\FTP";
    //ftp连接配置
    @Value("${FTPUserName}")
    private String userName;
    @Value("${FTPPassWord}")
    private String passWord;
    @Value("${FTPIp}")
    private String ftpIp;
    @Value("${FTPCharset}")
    private String ftpCharset;
    @Value("${FTPPort}")
    private int ftpPort;





    /**
     * ftp服务器上传文件
     */
    @ApiOperation(value = "上传文件", notes = "返回字段说明:\n" +
            "上传文件框name=file\n" +
            "上传是否成功")
    @ApiImplicitParam(name = "filePath", value = "文件路径", dataType = "String", required = true, paramType = "path")
    @PostMapping(value = "/fileUpload/{filePath}")
    @ResponseBody
    public void uploadFile(HttpServletRequest request, @PathVariable("filePath") String filePath) throws Exception {
        Ftp ftp = null;
        Charset charset = Charset.forName(ftpCharset);
        if (StrUtil.isNotBlank(userName) && StrUtil.isNotBlank(passWord)) {
            ftp = new Ftp(ftpIp, ftpPort, userName, passWord, charset).setMode(FtpMode.Passive); // 被动连接ftp
        } else {
            ftp = new Ftp(ftpIp, ftpPort).setMode(FtpMode.Passive);
        }
        //将路径参数中的%25替换为/
        filePath = filePath.replaceAll("%", "/");
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            MultipartFile multipartFile = multipartHttpServletRequest.getFile("file");
            String filename = multipartFile.getOriginalFilename();
            File file = MultipartFileToFile.multipartFileToFile(multipartFile);
            ftp.upload(filePath, filename, file);
        }
        System.out.println("上传成功");


    }


    /**
     * ftp服务器中下载文件
     */
    @ApiOperation(value = "下载文件", notes = "返回字段说明:\n" +
            "下载文件内容")
    @ApiImplicitParam(name = "filePath", value = "文件路径", dataType = "String", required = true, paramType = "path")
    @GetMapping("/filelevelDownload/{filePath}")
    @ResponseBody
    public String downloadFile(@PathVariable("filePath") String filePath ) {

        Ftp ftp = null;

        Charset charset = Charset.forName(ftpCharset);

        File file = new File(loadPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (StrUtil.isNotBlank(userName) && StrUtil.isNotBlank(passWord)) {
            ftp = new Ftp(ftpIp, ftpPort, userName, passWord, charset).setMode(FtpMode.Passive); // 被动连接ftp
        } else {
            ftp = new Ftp(ftpIp, ftpPort).setMode(FtpMode.Passive);
        }
        //ThreadPool ftp = ftpClient.getFtp();
        System.out.println("download file will begin...");
        //将路径参数中的%25替换为/
        filePath = filePath.replaceAll("%", "/");
        //从路径参数中获取文件名称
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        System.out.println(fileName);
        //从路径参数中获取文件ftp地址路径
        String ftpPathName = filePath.substring(0, filePath.lastIndexOf("/"));
        OutputStream os = null;
        try {
            response.setContentType("application/force-download");// 设置强制下载不打开
            //设置Headers
            response.setHeader("Content-Type", "application/octet-stream");
            os = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ftp.download(ftpPathName, fileName, os);

        System.out.println("下载成功");
        return "下载";


    }


    /**
     * ftp服务器中下载文件并预览
     */
    @ApiOperation(value = "查看文件内容", notes = "返回字段说明:\n" +
            "查看文件内容")
    @ApiImplicitParam(name = "filePath", value = "文件路径", dataType = "String", required = true, paramType = "path")
    @GetMapping("/toPdfFile/{filePath}")//"%1111%execl表格.xlsx"
    @ResponseBody
//    @Async
    public String toPdfFile(@PathVariable("filePath") String filePath ) throws IOException {




        Ftp ftp = null;

        Charset charset = Charset.forName(ftpCharset);


        if (StrUtil.isNotBlank(userName) && StrUtil.isNotBlank(passWord)) {
            ftp = new Ftp(ftpIp, ftpPort, userName, passWord, charset).setMode(FtpMode.Passive); // 被动连接ftp
        } else {
            ftp = new Ftp(ftpIp, ftpPort).setMode(FtpMode.Passive);
        }

//        FTPClient client = ftp.getClient();

        System.out.println("download file will begin...");
        //将路径参数中的%替换为/
        filePath = filePath.replaceAll("%", "/");
        //从路径参数中获取文件名称
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        //从路径参数中获取文件ftp地址路径
        String ftpPathName = filePath.substring(0, filePath.lastIndexOf("/"));
        System.out.println(ftpPathName);
//        ftp.download("/1111","出师表.docx",new File(rootPath));
        //转移到FTP服务器目录
//        client.changeWorkingDirectory(ftpPathName);
//        FTPFile[] ftpFiles = client.listFiles();

        ftp.cd(ftpPathName);
        List<String> ls = ftp.ls("/");
        for (String ftpFileName : ls) {
            if (fileName.equals(ftpFileName)) {
                File file = new File("C:\\Users\\cq\\Desktop\\doc\\" + fileName);
                OutputStream is = new FileOutputStream(file);
                ftp.download(ftpPathName, fileName, is);
//                client.retrieveFile(new String (fileName.getBytes("GBK"),"UTF-8"), is);
                is.flush();
                is.close();
            }

        }


        String[] split = filePath.split("\\.");

        String fileType = split[1];
        //图片格式的文件，直接访问url预览
        if ("jpg".equals(fileType) || "jpeg".equals(fileType) || "png".equals(fileType) || "gif".equals(fileType) || "bmp".equals(fileType) || "svg".equals(fileType) || "pdf".equals(fileType)) {
            File fileCon = new File("C:\\Users\\cq\\Desktop\\doc\\" + fileName);
            String s = "C:\\Users\\cq\\Desktop\\doc\\" + fileName;
            String fileUrl = "ftp://192.168.2.177" + filePath;
            //读取本地图片输入流
            FileInputStream inputStream = new FileInputStream(s);
            int i = inputStream.available();
            //byte数组用于存放图片字节数据
            byte[] buff = new byte[i];
            inputStream.read(buff);
            //记得关闭输入流
            inputStream.close();
            //设置发送到客户端的响应内容类型
//            response.setContentType("image/*");
            OutputStream out = response.getOutputStream();
            out.write(buff);
            //关闭响应输出流
            out.close();
        } else if ("doc".equals(fileType) || "vsd".equals(fileType) || "docx".equals(fileType) || "xls".equals(fileType) || "xlsx".equals(fileType) || "txt".equals(fileType) || "ppt".equals(fileType) || "pptx".equals(fileType)) {
            File fileCon = new File("C:\\Users\\cq\\Desktop\\doc\\" + fileName);
            try {
                //转换之后文件生成的地址
                File newFile = new File(rootPath);
                if (!newFile.exists()) {
                    newFile.mkdirs();
                }
                //文件转化
                converter.convert(fileCon).to(new File(rootPath + "/" + "hello.pdf")).execute();
                //使用response,将pdf文件以流的方式发送的前段
                ServletOutputStream outputStream = response.getOutputStream();
                InputStream in = new FileInputStream(new File(rootPath + "/" + "hello.pdf"));// 读取文件
                // copy文件
                int i = IOUtils.copy(in, outputStream);
                System.out.println(i);
                in.close();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "格式不支持预览";
    }


    /**
     * 获取全部文件夹层级目录
     */
    @ApiOperation(value = "文件层级对象", notes = "返回字段说明:\n" +
            "返回字段说明:id与parent_id对应\n" +
            "List=数据库中全部文件层级")
    @GetMapping("/filelevel")
    @ResponseBody
    public ResultUtil getAllFileLevels() throws IOException {
        Ftp ftp = null;

        Charset charset = Charset.forName(ftpCharset);
        if (StrUtil.isNotBlank(userName) && StrUtil.isNotBlank(passWord)) {
            ftp = new Ftp(ftpIp, ftpPort, userName, passWord ,charset).setMode(FtpMode.Passive); // 被动连接ftp
        } else {
            ftp = new Ftp(ftpIp, ftpPort).setMode(FtpMode.Passive);
        }
        FTPClient client = ftp.getClient();
        client.setControlEncoding("GBK");
        //第一级目录
        ftp.cd("/");
        String[] strings = client.listNames();
        List<Object> childrenFile = new ArrayList<>();
        List<Object> childrenFile1 = new ArrayList<>();
        if (strings != null) {
            for (String string : strings) {
                childrenFile1 = getChildrenFile(strings, ftp, client);
                if (strings.length == 1) {
                    childrenFile.addAll(childrenFile1);
                }
            }
        }
        if (strings.length == 1) {
            return ResultUtil.ok(childrenFile);
        } else {
            return ResultUtil.ok(childrenFile1);
        }
    }

    public List<Object> getChildrenFile(String[] strings, Ftp ftp, FTPClient client) throws
            IOException {
        List<Object> list = new ArrayList<>();
        if (strings != null) {
            for (String string : strings) {
                JSONObject obj = new JSONObject();
                obj.put("title", string);
                System.out.println("文件名称" + string);
                String pwd = ftp.pwd();
                System.out.println(ftp.pwd());
                boolean cd = ftp.cd(pwd + "/" + string);
                System.out.println(ftp.pwd());
                String[] strings1 = client.listNames();
                if (cd) {
                    System.out.println("内部文件夹:" + strings1);
                    obj.put("children", getChildrenFile(strings1, ftp, client));
                    ftp.cd("../");
                    System.out.println(ftp.pwd());
                }
                obj.put("address", ftp.pwd().replaceAll("/", "%25") + "%25" + string);
                System.out.println(obj);
                list.add(obj);
            }
        }
        return list;
    }
}