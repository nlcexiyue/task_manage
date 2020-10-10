package com.chuangqi.service;

import com.chuangqi.entity.News;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface NewsService {
    /**
     * 首页展示的通知公告列表,展示的字段:id,title,create_time,update_time
     */
    List<News> getHomeNews();

    /**
     * 根据时间区间来查询日程列表
     */
    List<News> getNewsByTime(String beginTime , String endTime);

    /**
     * 查询全部公告
     */
    List<News> getAllNews(String title ,String beginTime, String endTime);

    /**
     * 新增通知公告
     */
    Boolean addNews(News news);

    /**
     * 根据通知公告id来查询
     */
    News getNewsById(Integer id);

    /**
     * 查询指定用户发布过的通知公告
     */
    List<News> getNewsByUserId(Integer userId);

    /**
     * 根据id来修改通知公告
     */
    Boolean updateNews(News news);

    /**
     * 根据通知公告id来进行删除操作
     */
    Boolean deleteNews(Integer id);

    /**
     * 根据title , article 来查询
     */
    List<News> getNewsByTitleAndArticle(String title , String article);

    /**
     * 根据创建时间createTime来查询
     */
    News getNewsByCreateTime(String createTime);

    /**
     * 根据创建时间来进行查询
     */
    News getNewsByUpdateTime(String updateTime);

    /**
     * 根据id来进行修改新闻对象的picture_url字段
     */
    Boolean updateNewsForPictureUrl(Integer id ,String pictureUrl);
}
