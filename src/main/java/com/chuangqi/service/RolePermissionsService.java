package com.chuangqi.service;

import com.chuangqi.entity.RolePermissions;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionsService {
    /**
     * 在角色权限桥表中,根据角色id来查询
     */
    List<RolePermissions> getRolePermissionsByRoleId(Integer roleId);

    /**
     * 给角色分配权限,在单独的界面中,角色管理界面,分配角色拥有的权限
     */
    Boolean addRolePermissions(Integer roleId , Integer permissionsId);

}
