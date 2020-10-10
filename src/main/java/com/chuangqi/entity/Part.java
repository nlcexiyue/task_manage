package com.chuangqi.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/2/6
 * \* Time: 11:08
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Data
@ApiModel
@ToString
public class Part {
    /**
     * 科室id
     */
    private Integer partId;

    /**
     * 科室名称
     */
    private String partName;

    /**
     * 科室信息详情
     */
    private String article;

    /**
     * 科室信息创建时间
     */
    private Date createTime;

    /**
     * 科室信息url
     */
    private String pictureUrl;
}