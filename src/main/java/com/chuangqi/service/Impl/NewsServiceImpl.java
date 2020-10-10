package com.chuangqi.service.Impl;

import com.chuangqi.entity.News;
import com.chuangqi.entity.Notice;
import com.chuangqi.mapper.NewsMapper;
import com.chuangqi.service.NewsService;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/1/19
 * \* Time: 10:20
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Resource
    private NewsMapper newsMapper;

    @Override
    public List<News> getHomeNews() {
        return newsMapper.getHomeNews();
    }

    /**
     * 首页展示的通知公告列表,展示的字段:id,title,create_time,update_time
     */




    @Override
    public List<News> getAllNews(String title , String beginTime,String endTime) {
        return newsMapper.getAllNews(title ,beginTime , endTime);
    }


    /**
     * 根据时间区间来查询日程列表
     */
    @Override
    public List<News> getNewsByTime(String beginTime, String endTime) {
        return newsMapper.getNewsByTime(beginTime, endTime);
    }

    /**
     * 新增通知公告
     */
    @Override
    public Boolean addNews(News news) {
        Integer i = newsMapper.addNews(news);
        if(i!=0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 根据通知公告id来查询
     */
    @Override
    public News getNewsById(Integer id) {
        return newsMapper.getNewsById(id);
    }

    /**
     * 查询指定用户发布过的通知公告
     */
    @Override
    public List<News> getNewsByUserId(Integer userId) {
        return newsMapper.getNewsByUserId(userId);
    }

    /**
     * 根据id来修改通知公告
     */
    @Override
    public Boolean updateNews(News news) {
        Integer i = newsMapper.updateNews(news);
        if(i!=0){
            return true;
        }else {
            return false;
        }

    }

    /**
     * 根据通知公告id来进行删除操作
     */
    @Override
    public Boolean deleteNews(Integer id) {
        Integer i = newsMapper.deleteNews(id);
        if(i!=0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 根据title , article 来查询
     */
    @Override
    public List<News> getNewsByTitleAndArticle(String title, String article) {
        return newsMapper.getNewsByTitleAndArticle(title,article);
    }

    /**
     * 根据创建时间createTime来查询
     */
    @Override
    public News getNewsByCreateTime(String createTime) {
        return newsMapper.getNewsByCreateTime(createTime);
    }

    /**
     * 根据创建时间来进行查询
     */
    @Override
    public News getNewsByUpdateTime(String updateTime) {
        return newsMapper.getNewsByUpdateTime(updateTime);
    }

    /**
     * 根据id来进行修改新闻对象的picture_url字段
     */
    @Override
    public Boolean updateNewsForPictureUrl(Integer id ,String pictureUrl) {
        Integer i = newsMapper.updateNewsForPictureUrl(id ,pictureUrl);
        if(i!=0){
            return true;
        }else {
            return false;
        }
    }
}