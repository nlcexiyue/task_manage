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
 * \* Time: 14:55
 * \* To change this template use File | Settings | File Templates.
 * \* Description:角色权限桥表
 * \
 */
@Data
@ApiModel
@ToString
public class RolePermissions implements Serializable {
    /**
     * 角色权限桥表主键id
     */
    private Integer id;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 权限id
     */
    private Integer permissionsId;

    /**
     * 权限功能
     */
    private Permissions permissions;

    /**
     * 用户角色
     */
    private Role role;

}