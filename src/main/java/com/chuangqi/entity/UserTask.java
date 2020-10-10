package com.chuangqi.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/2/3
 * \* Time: 15:23
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@ApiModel
@Data
@ToString
public class UserTask {
    /**
     * 用户表id
     */
    private Integer id;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户工号
     */
    private String jobNumber;


    /**
     * 用户头衔
     */
    private String position;

    /**
     * 用户对应的日程周一
     */
    private List<Task> taskList1;

    /**
     * 用户对应的日程周二
     */
    private List<Task> taskList2;

    /**
     * 用户对应的日程周三
     */
    private List<Task> taskList3;

    /**
     * 用户对应的日程周四
     */
    private List<Task> taskList4;

    /**
     * 用户对应的日程周五
     */
    private List<Task> taskList5;

    /**
     * 用户对应的日程周六
     */
    private List<Task> taskList6;

    /**
     * 用户对应的日程周日
     */
    private List<Task> taskList7;


}