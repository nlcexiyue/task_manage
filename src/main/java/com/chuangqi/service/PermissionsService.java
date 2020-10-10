package com.chuangqi.service;

import com.chuangqi.entity.Permissions;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionsService {
    /**
     * 权限表的全表查询
     */
    List<Permissions> getAllPermissions();

    /**
     * 根据权限id查询对应的权限名称和详情
     */
    Permissions getPermissionsById(Integer id);
}
