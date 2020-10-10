package com.chuangqi.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/1/9
 * \* Time: 14:39
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@ApiModel
@Data
@ToString
public class User implements Serializable {
    //用户状态正常使用
    public static final int STATUS_NORMAL = 1;
    //用户状态禁用
    public static final int STATUS_DISABLE = 2;

    public static final int SEX_MALE = 0;
    public static final int SEX_FAMALE = 1;
    /**
     * 用户表id
     */
    private Integer id;

    /**
     * 用户登录名
     */
    private String username;

    /**
     * 用户登录密码
     */
    private String password;

    /**
     * 用户工号
     */
    private String jobNumber;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户头衔
     */
    private String position;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 注册时间
     */
    private Date createTime;

    /**
     * 个人邮箱
     */
    private String email;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 用户状态
     */
    private Integer status;

    /**
     * 用户照片,数据库字段为blob格式
     */
    private String pictureUrl;

    /**
     * 用户对应的角色
     */
    private UserRole userRole;

    /**
     * 用户对应的日程
     */
    private List<Task> taskList;


}