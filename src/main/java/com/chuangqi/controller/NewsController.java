package com.chuangqi.controller;

import com.chuangqi.entity.News;
import com.chuangqi.entity.Notice;
import com.chuangqi.entity.User;
import com.chuangqi.service.NewsService;
import com.chuangqi.tool.util.ResultUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
import java.util.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/1/19
 * \* Time: 10:24
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Api(tags = "新闻管理", description = "新闻管理接口详细信息的描述")
@RestController
@RequestMapping(value = "/news")
public class NewsController {
    @Autowired
    private NewsService newsService;
    @Value("${save.pic}")
    private String uploadPath;
    @Value("${nginx.url}")
    private String nginxUrl;

    SimpleDateFormat simpleDateFormat =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

    @ApiOperation(value = "时间区间查询新闻列表", notes = "返回字段说明:\n" +
            "以新闻表中的创建时间来进行区间查询\n"+
            "List=时间区间数据库中新闻列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beginTime", value = "查询的开始时间", dataType = "String", required = true, paramType = "path"),
            @ApiImplicitParam(name = "endTime", value = "查询的结束时间", dataType = "String", required = true, paramType = "path"),
            @ApiImplicitParam(name = "pageNum", value = "分页查询起始页", dataType = "int", required = true, paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "分页查询每页的个数", dataType = "int", required = true, paramType = "path")
    })
    @PostMapping(value = "/allNewsByTime")
    public ResultUtil getNewsByTime(String beginTime,String endTime , Integer pageNum , Integer pageSize){
        List<News> newsByTime = newsService.getNewsByTime(beginTime, endTime);
        //开启分页
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<News> pageInfo = new PageInfo<>(newsByTime);
        return ResultUtil.ok(pageInfo);
    }



    @ApiOperation(value = "首页展示新闻列表", notes = "返回字段说明:\n" +
            "List=数据库中新闻列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页查询起始页", dataType = "int", required = true, paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "分页查询每页的个数", dataType = "int", required = true, paramType = "path")
    })
    @PostMapping(value = "/allHomeNews")
    public ResultUtil getHomeNews(Integer pageNum , Integer pageSize){
        //开启分页
        PageHelper.startPage(pageNum, pageSize);
        List<News> news = newsService.getHomeNews();
        PageInfo<News> pageInfo = new PageInfo<>(news);
        return ResultUtil.ok(pageInfo);
    }

    @ApiOperation(value = "分页获取新闻列表", notes = "返回字段说明:\n" +
            "List=数据库中新闻列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "新闻标题查询条件", dataType = "String", required = true, paramType = "path"),
            @ApiImplicitParam(name = "pageNum", value = "分页查询起始页", dataType = "int", required = true, paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "分页查询每页的个数", dataType = "int", required = true, paramType = "path")
    })
    @PostMapping (value = "/newsByTitle")
    //@PathVariable("title") String title , @PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize
    public ResultUtil getAllNews(  Integer page , Integer limit , String title , String beginTime,String endTime){
        //给title查询条件添加%%,如果要传空值进行无条件查询,传入%,浏览器地址栏传入%
        System.out.println(page + "/" + limit + "/" + title + "/" + beginTime + "/" + endTime);

        title = "%" + title + "%";
        //开启分页
        PageHelper.startPage(page, limit);
        List<News> newsList = newsService.getAllNews(title , beginTime , endTime);
        PageInfo<News> pageInfo = new PageInfo<>(newsList);
        return ResultUtil.ok(0,pageInfo);
    }


    @ApiOperation(value = "新增新闻", notes = "返回字段说明:\n" +
            "上传的图片 name=file\n" +
            "是否新增成功")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "新闻标题", dataType = "String", required = true, paramType = "path"),
            @ApiImplicitParam(name = "article", value = "新闻内容", dataType = "String", required = true, paramType = "path")
    })
    @PostMapping(value = "/news")
    public ResultUtil addNews(HttpServletRequest request, News news) throws ParseException {

        //从session域中取出当前登录的用户
        User user = (User) request.getSession().getAttribute("user");
        //获取用户id
//        Integer userId = user.getId();
        news.setUserId(1);
        //新建一个时间
        Date date = new Date();


        //文章摘要截取
        String article = news.getArticle();
        String remark = "";
        if(article!=null && article!=""){
            if(article.length()>100){
                remark = article.substring(100)+ "...";
            }else{
                remark = article;
            }
        }
        news.setRemark(remark);

        //新增完成后,要查询出新增的这条新闻信息
        String dateString = simpleDateFormat.format(date);
        Date dateTime = simpleDateFormat.parse(dateString);

        news.setCreateTime(dateTime);
        news.setUpdateTime(dateTime);
        Boolean addResult = newsService.addNews(news);
//        List<News> newsList = new ArrayList<>();
        News newsResult = null;
        if(addResult){
//            newsList = newsService.getNewsByTitleAndArticle(news.getTitle(), news.getArticle());
            newsResult = newsService.getNewsByCreateTime(dateString);
            //取到新增的这个News对象的article
            String articleResult = newsResult.getArticle();
            //从article的富文本编辑器生成的标签中取出img标签
            String pictureUrlResult = "";
            String[] split = articleResult.split("src=");
            for (String s : split) {
                if(s.contains("http://")|| s.contains("https://")){
                    String substring = s.substring(0,s.lastIndexOf("/>"));
                    pictureUrlResult = pictureUrlResult + substring + ";";
                }
//            System.out.println(s);
            }
            //取到新增的这个News对象的id
            Integer idResult = newsResult.getId();
            newsService.updateNewsForPictureUrl(idResult , pictureUrlResult);

        }
        return ResultUtil.ok(newsResult);
    }


    @ApiOperation(value = "查看当前用户发布的新闻", notes = "返回字段说明:\n" +
            "List=当前用户发布过的新闻")
    @ApiImplicitParam(name = "userId", value = "用户id", dataType = "int", required = true, paramType = "path")
    @GetMapping(value = "/newsByUserId/{userId}")
    public ResultUtil getNewsByUserId(@PathVariable("userId") Integer userId){
        List<News> news = newsService.getNewsByUserId(userId);
        return ResultUtil.ok(news);
    }

    @ApiOperation(value = "根据id查询的新闻", notes = "返回字段说明:\n" +
            "指定id的新闻")
    @ApiImplicitParam(name = "id", value = "新闻id", dataType = "int", required = true, paramType = "path")
    @GetMapping(value = "/newsById/{id}")
    public ResultUtil getNewsById(@PathVariable("id") Integer id){
        News news = newsService.getNewsById(id);
        return ResultUtil.ok(news);
    }

    @ApiOperation(value = "修改新闻", notes = "返回字段说明:\n" +
            "只能修改自己发布的新闻,在管理页面的我的公告\n"+
            "是否修改成功")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "新闻标题", dataType = "String", required = true, paramType = "path"),
            @ApiImplicitParam(name = "article", value = "新闻内容", dataType = "String", required = true, paramType = "path"),
    })
    @PostMapping (value = "/updateNews")
    public ResultUtil updateNews(News news){
        //获取id将未修改的news对象查询出来
        Integer idResult = news.getId();
        News newsOld = newsService.getNewsById(idResult);

        //未更新前的新闻图片
        String pictureUrlOld = newsOld.getPictureUrl();
        //按;分割
        String[] pictureUrlOldArray = pictureUrlOld.split(";");
        Date date = new Date();
        news.setUpdateTime(date);
        //文章摘要截取
        String article = news.getArticle();
        String remark = "";
        if(article!=null && article!=""){
            if(article.length()>100){
                remark = article.substring(100)+ "...";
            }else{
                remark = article;
            }
        }
        news.setRemark(remark);
        //从article的富文本编辑器生成的标签中取出img标签
        String pictureUrlResult = "";
        String[] split = article.split("src=");
        for (String s : split) {
            if(s.contains("http://")|| s.contains("https://")){
                String substring = s.substring(0,s.lastIndexOf("/>"));
                pictureUrlResult = pictureUrlResult + substring + ";";
            }
        }
        //新的图片路径数组
        String[] pictureUrlNewArray = pictureUrlResult.split(";");

        //与未修改的图片数组进行比对,相同就保留服务器文件,不同就删除服务器文件

        for (String sOld : pictureUrlOldArray) {
            for (String sNew : pictureUrlNewArray) {
                if(!sOld.equals(sNew)){
                    String fileUrl = uploadPath + sOld.substring(nginxUrl.length());
                    File file = new File(fileUrl);
                    file.delete();
                }
            }


        }

        newsService.updateNewsForPictureUrl(idResult , pictureUrlResult);

        Boolean updateResult = newsService.updateNews(news);
        News newsResult = null;
        if(updateResult){
            newsResult = newsService.getNewsById(news.getId());

        }
        return ResultUtil.ok(newsResult);
    }

    @Transactional
    @ApiOperation(value = "根据id删除新闻", notes = "返回字段说明:\n" +
            "多个id删除,以','分割\n"+
            "删除指定id的新闻")
    @ApiImplicitParam(name = "ids", value = "多个新闻id", dataType = "String", required = true, paramType = "path")
    @DeleteMapping( value = "/news/{ids}")
    public ResultUtil deleteNews(@PathVariable("ids") String  ids){
        //ids是以','分割的字符串
        String[] split = ids.split(",");
        //标记一个删除成功的flag
        Boolean flag = true;
        for (String id : split) {
            //将分割后得到的id转为Integer类型
            Boolean deleteResult = newsService.deleteNews(new Integer(id));
            flag = flag && deleteResult;
            if(!flag){
                throw new RuntimeException(id + "的新闻删除失败");
            }
        }
        return ResultUtil.ok(flag);
    }

    @ApiOperation(value = "富文本编辑器的上传图片接口", notes = "返回字段说明:\n" +
            "在session中取出已登录用户的uuid\n"+
            "单独的异步上传接口")
    @PostMapping( value = "/newsUploadPicture")
    public Map<String , Object> uploadPicture(HttpServletRequest request){
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = multipartHttpServletRequest.getFiles("file");
        //生成一个uuid,用来存放通知公告的图片,作为文件夹名称
        String uuid = UUID.randomUUID().toString();
//        String uuid = (String)request.getSession().getAttribute("uuid");
        String pictureUrl = "";
        Map<String , Object> map  = new HashMap<>();
        for (MultipartFile multipartFile : files){
            //从请求表单数据中读取上传的文件名称
            String filename = multipartFile.getOriginalFilename();
            filename = filename.substring(filename.lastIndexOf("\\")+1);
            if(filename.contains(";")){
                throw new RuntimeException("上传文件名称不能带有;符号");
            }else{
                //创建存储图片的文件夹
                String folder = uploadPath + uuid + "/";
                File fileFolder = new File(folder);
                if (!fileFolder.exists()) {
                    fileFolder.mkdirs();
                }
                //每一个图片的路径
                pictureUrl = folder + filename;
                File file = new File(pictureUrl);
                //将上传的图片保存到本地文件夹中
                try {
                    multipartFile.transferTo(file);
                } catch (IOException e) {
                    map.put("error",1);
                    map.put("message","上传出错了");
                    return map;
                }
                pictureUrl = nginxUrl + pictureUrl.substring(9);
            }

        }
        map.put("error",0);
        map.put("url" ,pictureUrl );

        return map;
    }





}