package com.chuangqi.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/1/19
 * \* Time: 10:11
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Data
@ApiModel
@ToString
public class News implements Serializable {
    /**
     * 新闻id
     */
    private Integer id;

    /**
     * 创建用户id
     */
    private Integer userId;

    /**
     * 新闻标题
     */
    private String title;

    /**
     * 新闻摘要
     */
    private String remark;

    /**
     * 新闻详细内容
     */
    private String article;

    /**
     * 新闻创建时间
     */
    private Date createTime;

    /**
     * 新闻更新时间
     */
    private Date updateTime;


    /**
     * 新闻图片存储路径
     */
    private String pictureUrl;

    /**
     * 对应用户
     */
    private User user;
}