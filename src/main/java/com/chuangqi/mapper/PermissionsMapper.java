package com.chuangqi.mapper;

import com.chuangqi.entity.Permissions;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PermissionsMapper {
    /**
     * 权限表的全表查询
     */
    @Select("select * from permissions")
    @Results(id = "permissionsResult" , value = {
            @Result(property = "id" ,column = "id" , id = true),
            @Result(property = "permissions" ,column = "permissions")
    })
    List<Permissions> getAllPermissions();

    /**
     * 根据权限id查询对应的权限名称和详情
     */
    @Select("select * from permissions where id = #{id}")
    @ResultMap(value = "permissionsResult")
    Permissions getPermissionsById(@Param("id") Integer id);

}
