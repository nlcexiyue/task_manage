package com.chuangqi.service;

import com.chuangqi.entity.Notice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoticeService {

    /**
     * 首页展示的通知公告列表,展示的字段:id,title,create_time,update_time
     */
    List<Notice> getHomeNotices();

    /**
     * 查询全部公告
     */
    List<Notice> getAllNotices(String title ,String beginTime,String endTime);

    /**
     * 新增通知公告
     */
    Boolean addNotice(Notice notice);

    /**
     * 根据通知公告id来查询
     */
    Notice getNoticeById(Integer id );

    /**
     * 查询指定用户发布过的通知公告
     */
    List<Notice> getNotivesByUserId(Integer userId);

    /**
     * 根据id来修改通知公告
     */
    Boolean updateNotice(Notice notice);

    /**
     * 根据通知公告id来进行删除操作
     */
    Boolean deleteNotice( Integer id);
}
