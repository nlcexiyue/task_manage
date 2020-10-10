package com.chuangqi.service;

import com.chuangqi.entity.Task;
import com.chuangqi.entity.User;
import com.chuangqi.entity.UserTask;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.text.ParseException;
import java.util.List;

public interface TaskService {

    /**
     * 根据id来进行编辑日程
     */
    Boolean updateTask(Task  task);

    /**
     * 根据id来进行删除
     */
    Boolean deleteTask( Integer taskId);

    /**
     * 根据id来进行日程查询
     */
    Task getTaskById(Integer taskId);

    /**
     * 新增日程
     */
    Boolean addTask(Task task);

    /**
     * 根据用户表和日程表的联合查询
     */
    List<UserTask> getAllUserTask(String beginTime , String endTime);

    /**
     * 根据用户来进行日程查询
     */
    List<UserTask> getAllUserAndTask(String beginTime, String endTime) throws ParseException;

    /**
     * 查询全部日程列表
     */
    List<Task> getAllTasks();

    /**
     * 根据时间区间来查询日程列表
     */
    List<Task> getTaskByBeginTime(String beginTime ,String endTime);

    /**
     * 根据日程的user_id字段,来进行用户名称的模糊查询,查询结果为模糊用户对应的日程对象,结果是多个对象,加入日期时间限制
     */
    List<Task> getTaskByName(String name , String beginTime ,String endTime);
}
