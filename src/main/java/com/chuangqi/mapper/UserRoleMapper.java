package com.chuangqi.mapper;

import com.chuangqi.entity.UserRole;
import org.apache.ibatis.annotations.*;

public interface UserRoleMapper {
    /**
     * 根据用户id来查询对应的用户角色
     */
    @Select("select * from user_role where user_id = #{userId}")
    @Results(id = "userRoleByUserIdResult" , value = {
            @Result(property = "id" , column = "id" ,id = true),
            @Result(property = "userId" , column = "user_id"),
            @Result(property = "roleId" , column = "role_id"),
            @Result(property = "role" ,column = "role_id" , one = @One(select = "com.chuangqi.mapper.RoleMapper.getRoleById")),
            @Result(property = "rolePermissionsList" ,column = "role_id" , many = @Many(select = "com.chuangqi.mapper.RolePermissionsMapper.getRolePermissionsByRoleId"))
    })
    UserRole getRoleByUserId(@Param("userId") Integer userId);


    /**
     * 根据userId来查询用户所具有的对应的权限
     */
    @Select("select * from user_role where user_id = #{userId}")
    @Results(id = "userRoleResult" , value = {
            @Result(property = "id" , column = "id" ,id = true),
            @Result(property = "userId" , column = "user_id"),
            @Result(property = "roleId" , column = "role_id"),
            @Result(property = "user" , column = "user_id" , one = @One(select = "com.chuangqi.mapper.UserMapper.getUserById")),
            @Result(property = "rolePermissionsList" ,column = "role_id" , many = @Many(select = "com.chuangqi.mapper.RolePermissionsMapper.getRolePermissionsByRoleId"))

    })
    UserRole getUserRoleByUserId(@Param("userId") Integer userId);


    /**
     * 给用户分配角色
     */
    @Insert("insert into user_role (user_id , role_id) values (#{userId} ,#{roleId})")
    Integer addUserRole(@Param("userId") Integer userId , @Param("roleId") Integer roleId);

    /**
     * 修改用户的角色
     */
    @Update("update user_role set role_id = #{roleId} where user_id = #{userId} ")
    Integer updateUserRole(@Param("roleId") Integer roleId , @Param("userId") Integer userId );


}
