package com.chuangqi.service;

import com.chuangqi.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleService {
    /**
     * role表中根据id来查询角色名称详情
     */
    Role getRoleById(Integer id);

    /**
     * role表新增用户角色
     */
    Boolean addRole(String name ,String description);

    /**
     * 查询全部角色
     */
    List<Role> getAllRoles();

}
