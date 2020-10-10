package com.chuangqi.mapper;

import com.chuangqi.entity.Notice;
import org.apache.ibatis.annotations.*;
import org.aspectj.weaver.ast.Not;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.List;

public interface NoticeMapper {

    /**
     * 首页展示的通知公告列表,展示的字段:id,title,create_time,update_time
     */
    @Select("select id , title , create_time , update_time from notice order by create_time desc")
    @Results(id = "noticeHomeResult" , value = {
            @Result(property = "id" , column = "id" ,id = true),
            @Result(property = "title" , column = "title"),
            @Result(property = "createTime" , column = "create_time"),
            @Result(property = "updateTime" , column = "update_time")
    })
    List<Notice> getHomeNotices();


    /**
     * 查询全部公告
     */
    @Select({"<script>",
            "select * from notice",
            "<if test=\" title != '%%'  \">\twhere title like #{title}\n</if>",
            "<if test=\" title != '%%' and beginTime != '' and endTime != '' \">\tand update_time between #{beginTime} and #{endTime} \n</if>",
            "<if test=\" title == '%%' and beginTime != '' and endTime != '' \">\twhere update_time between #{beginTime} and #{endTime} \n</if>",
            "order by create_time desc",
            "</script>"})
    @Results(id = "noticeResult" , value = {
            @Result(property = "id" , column = "id" ,id = true),
            @Result(property = "userId" , column = "user_id" ),
            @Result(property = "title" , column = "title"),
            @Result(property = "article" , column = "article"),
            @Result(property = "createTime" , column = "create_time"),
            @Result(property = "updateTime" , column = "update_time"),
            @Result(property = "pictureUrl" , column = "picture_url"),
            @Result(property = "user" ,column = "user_id", one = @One(select = "com.chuangqi.mapper.UserMapper.getUserById"))
    })
    List<Notice> getAllNotices(@Param("title") String title ,@Param("beginTime") String beginTime,@Param("endTime") String endTime);

    /**
     * 新增通知公告
     */
    @Insert("insert into notice (user_id , title , article , create_time , update_time ,picture_url ) values(#{userId},#{title},#{article},#{createTime},#{updateTime} , #{pictureUrl})")
    Integer addNotice(Notice notice);

    /**
     * 根据通知公告id来查询
     */
    @Select("select * from notice where id = #{id}")
    @ResultMap(value = "noticeResult")
    Notice getNoticeById(@Param("id") Integer id );



    /**
     * 查询指定用户发布过的通知公告
     */
    @Select("select * from notice where user_id = #{userId} order by create_time desc")
    @ResultMap(value = "noticeResult")
    List<Notice> getNotivesByUserId(@Param("userId") Integer userId);


    /**
     * 根据id来修改通知公告
     */
    @Update("update notice set title =#{title},article=#{article},update_time = #{updateTime} ,picture_url = #{pictureUrl} where id = #{id}")
    Integer updateNotice(Notice notice);

    /**
     * 根据通知公告id来进行删除操作
     */
    @Delete("delete from notice where id = #{id}")
    Integer deleteNotice(@Param("id") Integer id);

}
