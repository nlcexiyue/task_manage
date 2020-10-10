package com.chuangqi.mapper;

import com.chuangqi.entity.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper {
    /**
     * role表中根据id来查询角色名称详情
     */
    @Select("select * from role where id = #{id}")
    Role getRoleById(@Param("id") Integer id);

    /**
     * role表新增用户角色
     */
    @Insert("insert into role (name , description) values (#{name} , #{description})")
    Integer addRole(@Param("name") String name , @Param("description") String description);

    /**
     * 查询全部角色
     */
    @Select("select * from role")
    List<Role> getAllRoles();

}
