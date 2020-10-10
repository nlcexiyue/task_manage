package com.chuangqi.service.Impl;

import com.chuangqi.entity.UserRole;
import com.chuangqi.mapper.UserRoleMapper;
import com.chuangqi.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/1/13
 * \* Time: 14:24
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 根据userId来查询
     */
    @Override
    public UserRole getUserRoleByUserId(Integer userId) {
        return userRoleMapper.getUserRoleByUserId(userId);
    }

    /**
     * 给用户分配角色
     */
    @Override
    public Boolean addUserRole(Integer userId, Integer roleId) {
        Integer i = userRoleMapper.addUserRole(userId, roleId);
        if(i!=0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 修改用户的角色
     */
    @Override
    public Boolean updateUserRole(Integer roleId, Integer userId) {
        Integer i = userRoleMapper.updateUserRole(roleId, userId);
        if(i!=0){
            return true;
        }else {
            return false;
        }
    }
}