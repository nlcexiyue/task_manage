package com.chuangqi.service.Impl;

import com.chuangqi.entity.Permissions;
import com.chuangqi.mapper.PermissionsMapper;
import com.chuangqi.service.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/1/13
 * \* Time: 14:36
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
public class PermissionsServiceImpl implements PermissionsService {

    @Resource
    private PermissionsMapper permissionsMapper;

    /**
     * 权限表的全表查询
     */
    @Override
    public List<Permissions> getAllPermissions() {
        return permissionsMapper.getAllPermissions();
    }

    /**
     * 根据权限id查询对应的权限名称和详情
     */
    @Override
    public Permissions getPermissionsById(Integer id) {
        return permissionsMapper.getPermissionsById(id);
    }
}