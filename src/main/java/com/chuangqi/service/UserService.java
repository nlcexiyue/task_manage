package com.chuangqi.service;

import com.chuangqi.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserService {

    /**
     * 禁用的人员列表
     */
    List<User> getAllForbiddenUser();

    /**
     * 用户注册
     */
    Boolean userRegister(User user);

    /**
     * 修改用户密码
     */
    Boolean userModify( Integer id , String password);

    /**
     * 通过id来查询对应用户
     */
    User getUserById(Integer id);

    /**
     * 通过用户登录名和用户登录密码来查询对象,以验证登录
     */
    User getUserByLogin( String username, String password);

    /**
     * 展示所有人员列表,其中多表联合包括,用户角色表对应的角色
     */
    List<User> getAllUserList(String name);

    /**
     * 用户上传头像图片
     */
    Boolean addUserPicture( String pictureUrl , Integer id);

    /**
     * 用户禁用
     */
    Boolean forbiddenUser( Integer status , Integer id);

    /**
     * 根据id来修改用户信息
     */
    Boolean updateUserInfo(User user);
}
