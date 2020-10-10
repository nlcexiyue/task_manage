package com.chuangqi.mapper;

import com.chuangqi.entity.News;
import com.chuangqi.entity.Notice;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface NewsMapper {

    /**
     * 首页展示的通知公告列表,展示的字段:id,title,create_time,update_time
     */
    @Select("select id , title , create_time , update_time from news order by create_time desc")
    @Results(id = "newsHomeResult", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "title", column = "title"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time")
    })
    List<News> getHomeNews();

    /**
     * 查询全部新闻
     */
    @Select({"<script>",
            "select * from news",
            "<if test=\" title != '%%'  \">\twhere title like #{title}\n</if>",
            "<if test=\" title != '%%' and beginTime != '' and endTime != '' \">\tand update_time between #{beginTime} and #{endTime} \n</if>",
            "<if test=\" title == '%%' and beginTime != '' and endTime != '' \">\twhere update_time between #{beginTime} and #{endTime} \n</if>",
            "order by create_time desc",
            "</script>"})
    @Results(id = "newsResult", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "title", column = "title"),
            @Result(property = "remark" ,column = "remark"),
            @Result(property = "article", column = "article"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "pictureUrl", column = "picture_url"),
            @Result(property = "user" ,column = "user_id", one = @One(select = "com.chuangqi.mapper.UserMapper.getUserById"))
    })
    List<News> getAllNews(@Param("title") String title , @Param("beginTime") String beginTime, @Param("endTime")String endTime);

    /**
     * 根据时间区间来查询日程列表
     */
    @Select("select * from news where update_time between #{beginTime} and #{endTime}")
    @ResultMap(value = "newsResult")
    List<News> getNewsByTime(@Param("beginTime")String beginTime , @Param("endTime") String endTime);

    /**
     * 新增通知公告
     */
    @Insert("insert into news (user_id , title , remark , article , create_time , update_time ,picture_url ) values(#{userId},#{title},#{remark},#{article},#{createTime},#{updateTime} , #{pictureUrl})")
    Integer addNews(News news);

    /**
     * 根据通知公告id来查询
     */
    @Select("select * from news where id = #{id}")
    @ResultMap(value = "newsResult")
    News getNewsById(@Param("id") Integer id);

    /**
     * 查询指定用户发布过的通知公告
     */
    @Select("select * from news where user_id = #{userId} order by create_time desc")
    @ResultMap(value = "newsResult")
    List<News> getNewsByUserId(@Param("userId") Integer userId);

    /**
     * 根据id来修改通知公告
     */
    @Update("update news set title =#{title},article=#{article}, remark = #{remark} , update_time = #{updateTime} where id = #{id}")
    Integer updateNews(News news);

    /**
     * 根据通知公告id来进行删除操作
     */
    @Delete("delete from news where id = #{id}")
    Integer deleteNews(@Param("id") Integer id);

    /**
     * 根据title , article 来查询
     */
    @Select("select * from news where title = #{title}  and article = #{article}")
    @ResultMap(value = "newsResult")
    List<News> getNewsByTitleAndArticle(@Param("title") String title , @Param("article") String article);

    /**
     * 根据创建时间createTime来查询
     */
    @Select("select * from news where create_time = #{createTime}")
    @ResultMap(value = "newsResult")
    News getNewsByCreateTime(@Param("createTime") String createTime);

    /**
     * 根据创建时间来进行查询
     */
    @Select("select * from news where update_time = #{updateTime}")
    @ResultMap(value = "newsResult")
    News getNewsByUpdateTime(@Param("updateTime") String updateTime);

    /**
     * 根据id来进行修改新闻对象的picture_url字段
     */
    @Update("update news set picture_url = #{pictureUrl} where id = #{id} ")
    Integer updateNewsForPictureUrl(@Param("id") Integer id , @Param("pictureUrl") String pictureUrl);
}
