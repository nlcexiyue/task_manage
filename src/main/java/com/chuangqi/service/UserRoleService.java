package com.chuangqi.service;

import com.chuangqi.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface UserRoleService {
    /**
     * 根据userId来查询
     */
    UserRole getUserRoleByUserId(Integer userId);

    /**
     * 给用户分配角色
     */
    Boolean addUserRole(Integer userId , Integer roleId);

    /**
     * 修改用户的角色
     */
    Boolean updateUserRole( Integer roleId , Integer userId );

}
