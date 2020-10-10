package com.chuangqi.mapper;

import com.chuangqi.entity.Task;
import com.chuangqi.entity.User;
import com.chuangqi.entity.UserTask;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TaskMapper {

    /**
     * 根据id来进行编辑日程
     */
    @Update("update  admin_task set title = #{title} , begin_time = #{beginTime} , end_time = #{endTime} where task_id = #{taskId}")
    Integer updateTask(Task  task);

    /**
     * 根据id来进行删除
     */
    @Delete("delete from admin_task where task_id = #{taskId}")
    Integer deleteTask(@Param("taskId") Integer taskId);


    /**
     * 根据id来进行日程查询
     */
    @Select("select * from admin_task where task_id = #{taskId}")
    Task getTaskById(@Param("taskId")Integer taskId);

    /**
     * 新增日程
     */
    @Insert("insert into admin_task (user_id , title  , create_time , begin_time , end_time) " +
            "values (#{userId} , #{title}  , #{createTime} , #{beginTime} , #{endTime})")
    Integer addTask(Task task);

    /**
     * 根据用户表和日程表的联合查询
     */
    @Select("select * from (\n" +
            "\tselect w.id,w.name from user as w \n" +
            ") as a\n" +
            "left join (\n" +
            "\tselect * from admin_task where  begin_time between #{beginTime} and #{endTime}\n" +
            ") as b\n" +
            "on a.id = b.user_id")
    @Results(id = "userTaskResult" ,value = {
            @Result(property = "id" , column = "task_id" ,id = true),
            @Result(property = "name" , column = "name"),
            @Result(property = "title" , column = "title"),
            @Result(property = "schedule" , column = "schedule"),
            @Result(property = "createTime" , column = "create_time"),
            @Result(property = "beginTime" , column = "begin_time"),
            @Result(property = "endTime" , column = "end_time")
    })
    List<UserTask> getAllUserTask(@Param("beginTime")String beginTime , @Param("endTime") String endTime);

    /**
     * 根据用户来进行日程查询
     */
    @Select("select * from user where status = '1'")
    @Results(id = "userAndTaskResult" ,value = {
            @Result(property = "id" , column = "id" ,id = true),
            @Result(property = "name" , column = "name"),
            @Result(property = "jobNumber" , column = "job_number"),
            @Result(property = "position" , column = "position"),
            @Result(property = "taskList" ,column = "id" , many = @Many(select = "com.chuangqi.mapper.TaskMapper.getTasksByUserId") )
    })
    List<User> getAllUserAndTask();


    /**
     * 根据用户id和日期区间来进行日程查询
     */
    @Select("select task_id , user_id , title , DATE_FORMAT(create_time,'%Y-%m-%d %H:%i:%s') as create_time, " +
            "DATE_FORMAT(begin_time,'%Y-%m-%d %H:%i:%s') as begin_time , DATE_FORMAT(end_time,'%Y-%m-%d %H:%i:%s') as end_time from admin_task where user_id = #{userId} ")
    @ResultMap(value = "taskByIserIdResult2")
    List<Task> getTasksByUserId(@Param("userId") Integer userId );

    /**
     * 根据日期区间进行查询
     */
    @Select("select * from admin_task where user_id = #{userId} and begin_time between #{beginTime} and #{endTime}")
    @Results(id = "taskByIserIdResult2" , value = {
            @Result(property = "taskId" , column = "task_id" , id = true),
            @Result(property = "userId" , column = "user_id"),
            @Result(property = "title" , column = "title"),
            @Result(property = "schedule" , column = "schedule"),
            @Result(property = "createTime" , column = "create_time"),
            @Result(property = "beginTime" , column = "begin_time"),
            @Result(property = "endTime" , column = "end_time")
    })
    List<Task> getTasksByUserId2( @Param("userId") Integer userId  , @Param("beginTime")String beginTime , @Param("endTime") String endTime);
    /**
     * 查询全部日程列表
     */
    @Select("select * from admin_task")
    @Results(id = "taskResult" , value = {
            @Result(property = "taskId" , column = "task_id" , id = true),
            @Result(property = "userId" , column = "user_id"),
            @Result(property = "title" , column = "title"),
            @Result(property = "schedule" , column = "schedule"),
            @Result(property = "createTime" , column = "create_time"),
            @Result(property = "beginTime" , column = "begin_time"),
            @Result(property = "endTime" , column = "end_time"),
            @Result(property = "user" , column = "user_id" ,one = @One(select = "com.chuangqi.mapper.UserMapper.getUserById"))
    })
    List<Task> getAllTasks();

    /**
     * 根据时间区间来查询日程列表
     */
    @Select("select * from admin_task where begin_time between #{beginTime} and #{endTime}")
    @ResultMap(value = "taskResult")
    List<Task> getTaskByBeginTime(@Param("beginTime")String beginTime , @Param("endTime") String endTime);

    /**
     * 根据日程的user_id字段,来进行用户名称的模糊查询,查询结果为模糊用户对应的日程对象,结果是多个对象,加入日期时间限制
     */
    @Select("select * from  (\n" +
            "\tselect * from admin_task \n" +
            ") as a\n" +
            "left join\n" +
            "(select * from user where name like #{name}) as b\n" +
            "on a.user_id = b.id" +
            "where begin_time between #{beginTime} and #{endTime}")
    @ResultMap(value = "taskResult")
    List<Task> getTaskByName(@Param("name") String name , @Param("beginTime")String beginTime , @Param("endTime") String endTime);
}
