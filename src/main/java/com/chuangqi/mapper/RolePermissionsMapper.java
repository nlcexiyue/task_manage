package com.chuangqi.mapper;

import com.chuangqi.entity.RolePermissions;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RolePermissionsMapper {
    /**
     * 在角色权限桥表中,根据角色id来查询
     */
    @Select("select * from role_permissions where role_id = #{roleId}")
    @Results(id = "rolePermissionsResult" , value = {
            @Result(property = "id" ,column = "id" ,id = true),
            @Result(property = "roleId" ,column = "role_id"),
            @Result(property = "permissionsId" ,column = "permissions_id"),
            @Result(property = "permissions" ,column = "permissions_id" , one = @One(select = "com.chuangqi.mapper.PermissionsMapper.getPermissionsById"))
    })
    List<RolePermissions> getRolePermissionsByRoleId(@Param("roleId") Integer roleId);

    /**
     * 给角色分配权限,在单独的界面中,角色管理界面,分配角色拥有的权限
     */
    @Insert("insert into role_permissions (role_id , permissions_id) values (#{roleId} , #{permissionsId})")
    Integer addRolePermissions(@Param("roleId")Integer roleId , @Param("permissionsId")Integer permissionsId);



}
