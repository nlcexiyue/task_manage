package com.chuangqi.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/1/9
 * \* Time: 12:51
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Data
@ApiModel
@ToString
public class Task implements Serializable {
    /**
     * 日程id
     */
    private Integer taskId;

    /**
     * 创建日程用户id
     */
    private Integer userId;

    /**
     * 日程标题
     */
    private String title;

    /**
     * 日程创建时间
     */

    private String createTime;

    /**
     * 日程开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String beginTime;

    /**
     * 日程结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endTime;

    /**
     * 对应的user对象
     */
    private User user;
}