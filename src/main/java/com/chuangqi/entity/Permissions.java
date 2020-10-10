package com.chuangqi.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/1/9
 * \* Time: 15:43
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Data
@ApiModel
@ToString
public class Permissions implements Serializable {
    /**
     * 权限表主键id
     */
    private Integer id;

    /**
     * 权限名称
     */
    private String permissions;


}