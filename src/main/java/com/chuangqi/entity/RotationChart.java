package com.chuangqi.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/2/3
 * \* Time: 9:46
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Data
@ApiModel
@ToString
public class RotationChart implements Serializable {
    /**
     * 轮播图管理主键id
     */
    private Integer id;

    /**
     * 轮播图模块id
     */
    private Integer modelId;

    /**
     * 轮播图的图片名称
     */
    private String filename;

    /**
     * 轮播图图片存储路径
     */
    private String pictureUrl;

    /**
     * 轮播图图片存储时间
     */
    private Date createTime;
}