package com.chuangqi.service.Impl;

import com.chuangqi.entity.RolePermissions;
import com.chuangqi.mapper.RolePermissionsMapper;
import com.chuangqi.service.RolePermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/1/13
 * \* Time: 14:50
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
public class RolePermissionsServiceImpl implements RolePermissionsService {

    @Resource
    private RolePermissionsMapper rolePermissionsMapper;
    /**
     * 在角色权限桥表中,根据角色id来查询
     */
    @Override
    public List<RolePermissions> getRolePermissionsByRoleId(Integer roleId) {
        return rolePermissionsMapper.getRolePermissionsByRoleId(roleId);
    }

    /**
     * 给角色分配权限,在单独的界面中,角色管理界面,分配角色拥有的权限
     */
    @Override
    public Boolean addRolePermissions(Integer roleId, Integer permissionsId) {
        Integer i = rolePermissionsMapper.addRolePermissions(roleId, permissionsId);
        if(i!=0){
            return true;
        }else {
            return false;
        }
    }
}