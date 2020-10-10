package com.chuangqi.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/1/9
 * \* Time: 14:07
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Data
@ApiModel
@ToString
public class Notice implements Serializable {
    /**
     * 通知公告id
     */
    private Integer id;

    /**
     * 创建用户id
     */
    private Integer userId;

    /**
     * 通知公告标题
     */
    private String title;

    /**
     * 通知公告详细内容
     */
    private String article;

    /**
     * 通知公告创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 通知公告更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    /**
     * 通知公告图片存储路径
     */
    private String pictureUrl;

    /**
     * 对应用户信息
     */
    private User user;
}