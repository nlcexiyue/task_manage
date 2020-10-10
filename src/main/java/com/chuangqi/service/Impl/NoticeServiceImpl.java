package com.chuangqi.service.Impl;

import com.chuangqi.entity.Notice;
import com.chuangqi.mapper.NoticeMapper;
import com.chuangqi.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/1/9
 * \* Time: 14:17
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    @Override
    public List<Notice> getHomeNotices() {
        return noticeMapper.getHomeNotices();
    }

    /**
     * 首页展示的通知公告列表,展示的字段:id,title,create_time,update_time
     */
    @Override
    public List<Notice> getAllNotices(String title ,String beginTime,String endTime) {
        return noticeMapper.getAllNotices(title , beginTime , endTime);
    }

    /**
     * 新增通知公告
     */
    @Override
    public Boolean addNotice(Notice notice) {
        Integer i = noticeMapper.addNotice(notice);
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
    public Notice getNoticeById(Integer id) {
        return noticeMapper.getNoticeById(id);
    }

    /**
     * 查询指定用户发布过的通知公告
     */
    @Override
    public List<Notice> getNotivesByUserId(Integer userId) {
        return noticeMapper.getNotivesByUserId(userId);
    }

    /**
     * 根据id来修改通知公告
     */
    @Override
    public Boolean updateNotice(Notice notice) {
        Integer i = noticeMapper.updateNotice(notice);
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
    public Boolean deleteNotice(Integer id) {
        Integer i = noticeMapper.deleteNotice(id);
        if(i!=0){
            return true;
        }else {
            return false;
        }
    }


}