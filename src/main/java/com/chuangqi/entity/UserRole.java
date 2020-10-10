package com.chuangqi.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/1/9
 * \* Time: 14:40
 * \* To change this template use File | Settings | File Templates.
 * \* Description:用户角色桥表
 * \
 */
@Data
@ApiModel
@ToString
public class UserRole implements Serializable {

    /**
     * 用户角色表主键id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 用户对象
     */
    private User user;

    /**
     * 用户角色
     */
    private Role role;

    /**
     * 用户权限
     */
    private List<RolePermissions> rolePermissionsList;

}