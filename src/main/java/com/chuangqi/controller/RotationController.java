package com.chuangqi.controller;

import com.chuangqi.entity.RotationChart;
import com.chuangqi.service.RotationChartService;
import com.chuangqi.tool.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/2/3
 * \* Time: 9:37
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Api(tags = "轮播图管理", description = "首页轮播图管理接口详细信息的描述")
@RestController
@RequestMapping(value = "/rotation")
public class RotationController {

    @Autowired
    private RotationChartService rotationChartService;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Value("${save.pic}")
    private String uploadPath;
    @Value("${nginx.url}")
    private String nginxUrl;

    @ApiOperation(value = "轮播图展示", notes = "返回字段说明:\n" +
            "modelId的值-0:首页轮播图      2-4:科室轮播图\n"+
            "返回轮播图存储路径\n"
    )
    @ApiImplicitParam(name = "modelId", value = "轮播图模块id", dataType = "int", required = true, paramType = "path")
    @GetMapping(value = "/rotation/{modelId}")
    public ResultUtil getAllRotationChart(@PathVariable("modelId") Integer modelId) {
        List<RotationChart> allRotationChart = rotationChartService.getAllRotationChart();
        //新建一个集合,用于存储筛选轮播图对象
        List<RotationChart> pageRotationChart = new ArrayList<>();
        for (RotationChart rotationChart : allRotationChart) {
            Integer modelIdOld = rotationChart.getModelId();
            if(modelId.equals(modelIdOld)){
                pageRotationChart.add(rotationChart);
            }
        }
        return ResultUtil.ok(pageRotationChart);
    }

    @ApiOperation(value = "轮播图新增", notes = "返回字段说明:\n" +
            "modelId的值-1:首页轮播图      2-4:科室轮播图\n"+
            "存储轮播图存储路径\n"
    )
    @PostMapping(value = "/rotation")
    public ResultUtil addRotationChart(HttpServletRequest request , Integer modelId) throws ParseException {
        Date date = new Date();
        //防止存入数据库四舍五入
        String format = simpleDateFormat.format(date);
        Date dateNew = simpleDateFormat.parse(format);

        String pictureUrl = "";
        String filename = "";
        //上传轮播图图片,每次只能上传一张图片
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            MultipartFile multipartFile = multipartHttpServletRequest.getFile("file");
            //从请求表单数据中读取上传的文件名称
            filename = multipartFile.getOriginalFilename();
            filename = filename.substring(filename.lastIndexOf("\\")+1);
            //创建存储图片的文件夹
            String folder = uploadPath + "Rotation" + "/";
            File fileFolder = new File(folder);
            if (!fileFolder.exists()) {
                fileFolder.mkdirs();
            }
            pictureUrl = folder + filename;
            File file = new File(pictureUrl);
            //将上传的图片保存到本地文件夹中
            try {
                multipartFile.transferTo(file);
            } catch (IOException e) {
                throw new RuntimeException(filename + "上传出错了");
            }
        }
        Boolean addResult = false;
        if(pictureUrl!="" && filename!=""){
            //将本地路径转为nginx路径
            pictureUrl = nginxUrl + pictureUrl.substring(9);
            addResult = rotationChartService.addRotationChart(modelId, filename, pictureUrl ,dateNew);
            if(addResult){
                String createTime = simpleDateFormat.format(dateNew);
                RotationChart rotationChartByMore = rotationChartService.getRotationChartByMore(createTime);
                return ResultUtil.ok(rotationChartByMore);
            }


        }
        return ResultUtil.ok(addResult);
    }


    @ApiOperation(value = "轮播图删除", notes = "返回字段说明:\n" +
            "删除存储轮播图存储路径\n"
    )
    @Transactional
    @GetMapping(value = "/remove/{id}")
    public ResultUtil deleteRotationChart(@PathVariable("id")Integer id){
        //标记一个删除成功的flag
        Boolean flag = true;
        RotationChart rotationChart = rotationChartService.getRotationChart(id);
        if(rotationChart!=null){
            String pictureUrl = rotationChart.getPictureUrl();
            Boolean deleteResult = rotationChartService.deleteRotationChart(id);
            if(deleteResult){
                String fileUrl = uploadPath + pictureUrl.substring(nginxUrl.length());
                File file = new File(fileUrl);
                file.delete();
            }
            flag = flag && deleteResult;
            if (!flag) {
                throw new RuntimeException(id + "的轮播图删除失败");
            }
        }
        return ResultUtil.ok(flag);
    }





}