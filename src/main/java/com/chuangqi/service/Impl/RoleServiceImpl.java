package com.chuangqi.service.Impl;

import com.chuangqi.entity.Role;
import com.chuangqi.mapper.RoleMapper;
import com.chuangqi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/1/13
 * \* Time: 15:54
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;
    /**
     * role表中根据id来查询角色名称详情
     */
    @Override
    public Role getRoleById(Integer id) {
        return roleMapper.getRoleById(id);
    }

    /**
     * role表新增用户角色
     */
    @Override
    public Boolean addRole(String name, String description) {
        Integer i = roleMapper.addRole(name, description);
        if(i!=0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 查询全部角色
     */
    @Override
    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }
}