package com.chuangqi.controller;

import com.chuangqi.entity.Notice;
import com.chuangqi.entity.Task;
import com.chuangqi.entity.User;
import com.chuangqi.service.NoticeService;
import com.chuangqi.tool.util.ResultUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/1/9
 * \* Time: 14:18
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Api(tags = "通知公告管理", description = "通知公告管理接口详细信息的描述")
@RestController
@RequestMapping(value = "/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    @Value("${save.pic}")
    private String uploadPath;
    @Value("${nginx.url}")
    private String nginxUrl;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @ApiOperation(value = "首页展示通知公告列表", notes = "返回字段说明:\n" +
            "List=数据库中通知公告列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页查询起始页", dataType = "int", required = true, paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "分页查询每页的个数", dataType = "int", required = true, paramType = "path")
    })
    @PostMapping(value = "/allHomeNotices")
    public ResultUtil getHomeNotices(Integer pageNum, Integer pageSize) {
        //开启分页
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> notices = noticeService.getHomeNotices();
        PageInfo<Notice> pageInfo = new PageInfo<>(notices);
        return ResultUtil.ok(pageInfo);
    }


    @ApiOperation(value = "分页获取通知公告列表", notes = "返回字段说明:\n" +
            "List=数据库中通知公告列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "通知公告标题查询条件", dataType = "String", required = true, paramType = "path"),
            @ApiImplicitParam(name = "pageNum", value = "分页查询起始页", dataType = "int", required = true, paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "分页查询每页的个数", dataType = "int", required = true, paramType = "path")
    })
    @PostMapping(value = "/notices")
    //@PathVariable("title") String title , @PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize
    public ResultUtil getAllNotices(Integer page, Integer limit, String title ,String beginTime,String endTime) throws ParseException {
//        Integer pageNum = Integer.valueOf(request.getParameter("pageNum"));
//        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
//        String title = request.getParameter("title");
        //给title查询条件添加%%,如果要传空值进行无条件查询,传入%,浏览器地址栏传入%
        SimpleDateFormat simpleDateFormat =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        System.out.println(page + "/" + limit + "/" + title);
        title = "%" + title + "%";
        //开启分页
        PageHelper.startPage(page, limit);
        List<Notice> notices = noticeService.getAllNotices(title , beginTime , endTime);

        PageInfo<Notice> pageInfo = new PageInfo<>(notices);
        return ResultUtil.ok(0,pageInfo);
    }

    @Transactional
    @ApiOperation(value = "新增通知公告", notes = "返回字段说明:\n" +
            "上传的图片 name=file\n" +
            "是否新增成功")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "通知公告标题", dataType = "String", required = true, paramType = "path"),
            @ApiImplicitParam(name = "article", value = "通知公告内容", dataType = "String", required = true, paramType = "path")
    })
    @PostMapping(value = "/notice")
    public ResultUtil addNotice(HttpServletRequest request, Notice notice) throws ParseException {
        //从session域中取出当前登录的用户
        User user = (User) request.getSession().getAttribute("user");
        if(user!=null){

            String article = notice.getArticle();

            //获取用户id
            Integer userId = user.getId();
            notice.setUserId(userId);
            Date date = new Date();
            //将时间格式的数据防止四舍五入
            String format = simpleDateFormat.format(date);
            Date createTime = simpleDateFormat.parse(format);
            notice.setCreateTime(createTime);
            notice.setUpdateTime(createTime);

            //从article的富文本编辑器生成的标签中取出img标签
            String pictureUrlResult = "";
            String[] split = article.split("src=");
            if(split.length!=0){
                for (String s : split) {
                    if(s.contains("http://")|| s.contains("https://")){
                        String substring = s.substring(0,s.lastIndexOf("/>"));
                        pictureUrlResult = pictureUrlResult + substring + ";";
                    }
                }
            }

            notice.setPictureUrl(pictureUrlResult);
            Boolean addResult = noticeService.addNotice(notice);


            return ResultUtil.ok(0,addResult);
        }
        return ResultUtil.error(301,"用户未登录");


//        //生成一个uuid,用来存放通知公告的图片,作为文件夹名称
//        String uuid = UUID.randomUUID().toString();
//
//
//        //从session域中取出当前登录的用户
//        User user = (User) request.getSession().getAttribute("user");
//
//        if(user!=null){
//
//            //初始化存储路径
//            String pictureUrl = "";
//            pictureUrl = uploadPicture(request, uuid, pictureUrl);
//            //获取用户id
//            Integer userId = user.getId();
//            notice.setUserId(userId);
//            notice.setCreateTime(new Date());
//            notice.setUpdateTime(new Date());
//            notice.setPictureUrl(pictureUrl);
//            Boolean addResult = noticeService.addNotice(notice);
//            return ResultUtil.ok(addResult);
//        }
//        return ResultUtil.error(500,"用户未登录");

    }


    @ApiOperation(value = "查看当前用户发布的通知公告", notes = "返回字段说明:\n" +
            "List=当前用户发布过的通知公告")
    @ApiImplicitParam(name = "userId", value = "用户id", dataType = "int", required = true, paramType = "path")
    @GetMapping(value = "/noticeByUserId/{userId}")
    public ResultUtil getNotivesByUserId(@PathVariable("userId") Integer userId) {
        List<Notice> notives = noticeService.getNotivesByUserId(userId);
        return ResultUtil.ok(notives);
    }

    @ApiOperation(value = "根据id查询的通知公告", notes = "返回字段说明:\n" +
            "指定id的通知公告")
    @ApiImplicitParam(name = "id", value = "通知公告id", dataType = "int", required = true, paramType = "path")
    @GetMapping(value = "/noticeById/{id}")
    public ResultUtil getNoticeById(@PathVariable("id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        List<String> fileNameList = new ArrayList<>();
        List<String> pictureUrlList = new ArrayList<>();
        Notice notice = noticeService.getNoticeById(id);
        String pictureUrl = notice.getPictureUrl();
        if (pictureUrl != null && pictureUrl!="") {
            //将pictureUrl用;分割
            String[] split = pictureUrl.split(";");
            for (String s : split) {
                //原先保存的每个图片,截取到只剩下文件名称
                String fileName = s.substring(70);
                fileNameList.add(fileName);
                pictureUrlList.add(s);
            }
        }
        map.put("fileName", fileNameList);
        map.put("pictureUrl", pictureUrlList);
        map.put("notice", notice);

        return ResultUtil.ok(map);
    }


    @ApiOperation(value = "修改通知公告", notes = "返回字段说明:\n" +
            "只能修改自己发布的通知公告,在管理页面的我的公告\n" +
            "是否修改成功")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "通知公告标题", dataType = "String", required = true, paramType = "path"),
            @ApiImplicitParam(name = "article", value = "通知公告内容", dataType = "String", required = true, paramType = "path"),
            @ApiImplicitParam(name = "begin_time", value = "通知公告开始时间", dataType = "String", required = true, paramType = "path"),
            @ApiImplicitParam(name = "end_time", value = "通知公告结束时间", dataType = "String", required = true, paramType = "path"),
            @ApiImplicitParam(name = "oldUrl", value = "回显回去的pictureUrl", dataType = "String", required = true, paramType = "path")
    })
    @PostMapping(value = "/updateNotice")
    public ResultUtil updateNotice( Notice notice) {
        //获取通知id,查询出未修改的notice对象
        Integer id = notice.getId();
        Notice noticeOld = noticeService.getNoticeById(id);
        //首先更新时间
        notice.setUpdateTime(new Date());
        //未更新前的新闻图片
        String pictureUrlOld = noticeOld.getPictureUrl();
        //按;分割
        String[] pictureUrlOldArray = pictureUrlOld.split(";");
        String article = notice.getArticle();
        //从article的富文本编辑器生成的标签中取出img标签
        String pictureUrlResult = "";
        String[] split = article.split("src=\"");
        for (String s : split) {
            if(s.contains("http://")|| s.contains("https://")){
                String substring = s.substring(0,s.lastIndexOf("/>"));
                pictureUrlResult = pictureUrlResult + substring + ";";
            }
        }
        notice.setPictureUrl(pictureUrlResult);
        //新的图片路径数组
        String[] pictureUrlNewArray = pictureUrlResult.split(";");
        //与未修改的图片数组进行比对,相同就保留服务器文件,不同就删除服务器文件
        if(pictureUrlOld!=""){
            for (String sOld : pictureUrlOldArray) {
                for (String sNew : pictureUrlNewArray) {
                    if(!sOld.equals(sNew)){
                        String fileUrl = uploadPath + sOld.substring(nginxUrl.length());
                        File file = new File(fileUrl);
                        file.delete();
                    }
                }
            }
        }



        Boolean updateResult = noticeService.updateNotice(notice);


        Notice noticeResult = null;
        if(updateResult){
            noticeResult = noticeService.getNoticeById(id);

        }
        return ResultUtil.ok(0,noticeResult);


    }

    @Transactional
    @ApiOperation(value = "根据id删除通知公告", notes = "返回字段说明:\n" +
            "多个id删除,以','分割\n" +
            "删除指定id的通知公告")
    @ApiImplicitParam(name = "ids", value = "多个通知公告id", dataType = "String", required = true, paramType = "path")
    @DeleteMapping(value = "/notice/{ids}")
    public ResultUtil deleteNotice(@PathVariable("ids") String ids) {
        //ids是以','分割的字符串
        String[] split = ids.split(",");
        //标记一个删除成功的flag
        Boolean flag = true;
        for (String id : split) {
            Integer idResult = new Integer((id));
            //先取到id对应的数据库对象
            Notice notice = noticeService.getNoticeById(idResult);
            String pictureUrl = notice.getPictureUrl();
            //将分割后得到的id转为Integer类型
            Boolean deleteResult = noticeService.deleteNotice(idResult);
            //当数据库中字段删除成功时,才删除服务器的文件
            if(deleteResult && pictureUrl!=""){
                String[] pictureUrlArray = pictureUrl.split(";");
                for (String s : pictureUrlArray) {
                    String fileUrl = uploadPath + s.substring(nginxUrl.length());
                    File file = new File(fileUrl);
                    file.delete();

                }
            }
            flag = flag && deleteResult;
            if (!flag) {
                throw new RuntimeException(id + "的通知公告删除失败");
            }
        }
        return ResultUtil.ok(flag);
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