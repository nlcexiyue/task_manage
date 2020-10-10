package com.chuangqi.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/1/9
 * \* Time: 15:44
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Data
@ApiModel
@ToString
public class Role implements Serializable {
    /**
     * 角色表主键id
     */
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     *  角色描述
     */
    private String description;
}