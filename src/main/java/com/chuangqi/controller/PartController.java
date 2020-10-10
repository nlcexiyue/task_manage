package com.chuangqi.controller;

import com.chuangqi.entity.Part;
import com.chuangqi.service.PartService;
import com.chuangqi.tool.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/2/6
 * \* Time: 11:15
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Api(tags = "科室管理", description = "科室管理接口详细信息的描述")
@RestController
@RequestMapping(value = "/part")
public class PartController {

    @Autowired
    private PartService partService;

    @Value("${save.pic}")
    private String uploadPath;
    @Value("${nginx.url}")
    private String nginxUrl;

    SimpleDateFormat simpleDateFormat =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );


    @ApiOperation(value = "全部科室部门查询", notes = "返回字段说明:\n" +
            "全部科室部门")
    @GetMapping(value = "/part")
    public ResultUtil getAllPart(){
        List<Part> allPart = partService.getAllPart();
        return ResultUtil.ok(0,allPart);
    }

    @ApiOperation(value = "根据id科室部门查询", notes = "返回字段说明:\n" +
            "传入参数:Integer partId\n"+
            "科室部门")
    @PostMapping(value = "/partById")
    public ResultUtil getPartById(Integer partId){
        Part part = partService.getPartById(partId);
        return ResultUtil.ok(part);
    }

    @ApiOperation(value = "科室部门修改", notes = "返回字段说明:\n" +
            "是否成功修改科室部门")
    @PostMapping(value = "/updatePart")
    public ResultUtil updatePart(Part part){
        Integer partId = part.getPartId();

        String article = part.getArticle();
        //从article的富文本编辑器生成的标签中取出img标签
        String pictureUrlResult = "";
        String[] split = article.split("src=");
        for (String s : split) {
            if(s.contains("http://")|| s.contains("https://")){
                String substring = s.substring(0,s.lastIndexOf("/>"));
                pictureUrlResult = pictureUrlResult + substring + ";";
            }

        }
        part.setPictureUrl(pictureUrlResult);

        Boolean updateResult = partService.updatePart(part);
        Part partById = null;
        if(updateResult){
            partById = partService.getPartById(partId);
        }

        return ResultUtil.ok(partById);
    }

    @ApiOperation(value = "新增科室部门", notes = "返回字段说明:\n" +
            "传入参数科室名称:String partName\n"+
            "传入参数科室介绍:String article\n"+
            "新增科室部门")
    @PostMapping(value = "/addPart")
    public ResultUtil addPart(Part part){
        //文章摘要截取
        String article = part.getArticle();

        //从article的富文本编辑器生成的标签中取出img标签
        String pictureUrlResult = "";
        String[] split = article.split("src=");
        for (String s : split) {
            if(s.contains("http://")|| s.contains("https://")){
                String substring = s.substring(0,s.lastIndexOf("/>"));
                pictureUrlResult = pictureUrlResult + substring + ";";
            }

        }


        Date date = new Date();
        String dateFormat = simpleDateFormat.format(date);
        part.setCreateTime(date);
        part.setPictureUrl(pictureUrlResult);

        Boolean addResult = partService.addPart(part);
        Part partByCreateTime = null;
        if(addResult){
            partByCreateTime = partService.getPartByCreateTime(dateFormat);
        }


        return ResultUtil.ok(partByCreateTime);
    }

    @ApiOperation(value = "删除科室部门", notes = "返回字段说明:\n" +
            "传入参数:Integer partId\n"+
            "删除科室部门")
    @PostMapping(value = "/deletePart")
    public ResultUtil deletePart(Integer partId){
        Part part = partService.getPartById(partId);
        //删除本地存储的图片及文件夹
        if(part!=null){
            String pictureUrlList = part.getPictureUrl();
            if(pictureUrlList!=null && pictureUrlList!=""){
                String[] split = pictureUrlList.split(";");
                for (String s : split) {
                    String fileUrl = uploadPath + s.substring(nginxUrl.length());
                    File file = new File(fileUrl);
                    file.delete();

                }
            }

        }



        Boolean deleteResult = partService.deletePart(partId);
        return ResultUtil.ok(deleteResult);
    }



    //图片上传的方法
    private String uploadPicture(HttpServletRequest request, String uuid, String pictureUrl) {
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> files = multipartHttpServletRequest.getFiles("file");
            for (MultipartFile multipartFile : files) {
                //从请求表单数据中读取上传的文件名称
                String filename = multipartFile.getOriginalFilename();
                //创建存储图片的文件夹
                String folder = uploadPath + uuid + "/";
                File fileFolder = new File(folder);
                if (!fileFolder.exists()) {
                    fileFolder.mkdirs();
                }
                //每一个子图片的路径
                String pictureUrlSub = folder + filename;
                File file = new File(pictureUrlSub);
                //将上传的图片保存到本地文件夹中
                try {
                    multipartFile.transferTo(file);
                } catch (IOException e) {
                    throw new RuntimeException(filename + "上传出错了");
                }
                pictureUrlSub = nginxUrl + pictureUrlSub.substring(9);
                //将每个上传图片的路径拼接
                pictureUrl = pictureUrl + pictureUrlSub + ";";

            }
        }
        return pictureUrl;
    }



}