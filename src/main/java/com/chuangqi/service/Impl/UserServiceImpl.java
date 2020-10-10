package com.chuangqi.service.Impl;

import com.chuangqi.entity.User;
import com.chuangqi.mapper.UserMapper;
import com.chuangqi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/1/13
 * \* Time: 9:29
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 禁用的人员列表
     */
    @Override
    public List<User> getAllForbiddenUser() {
        return userMapper.getAllForbiddenUser();
    }

    /**
     * 用户注册
     */
    @Override
    public Boolean userRegister(User user) {
        Integer i = userMapper.userRegister(user);
        if(i!=0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 修改用户密码
     */
    @Override
    public Boolean userModify(Integer id , String password) {
        Integer i = userMapper.userModify(id, password);
        if(i!=0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 通过id来查询对应用户
     */
    @Override
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);

    }

    /**
     * 通过用户登录名和用户登录密码来查询对象,以验证登录
     */
    @Override
    public User getUserByLogin(String username, String password) {
        return userMapper.getUserByLogin(username, password);
    }

    /**
     * 展示所有人员列表,其中多表联合包括,用户角色表对应的角色
     */
    @Override
    public List<User> getAllUserList(String name) {
        return userMapper.getAllUserList(name);
    }

    /**
     * 用户上传头像图片
     */
    @Override
    public Boolean addUserPicture(String pictureUrl, Integer id) {
        Integer i = userMapper.addUserPicture(pictureUrl, id);
        if(i!=0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 用户禁用
     */
    @Override
    public Boolean forbiddenUser(Integer status, Integer id) {
        Integer i = userMapper.forbiddenUser(status, id);
        if(i!=0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 根据id来修改用户信息
     */
    @Override
    public Boolean updateUserInfo(User user) {
        Integer i = userMapper.updateUserInfo(user);
        if(i!=0){
            return true;
        }else {
            return false;
        }
    }
}