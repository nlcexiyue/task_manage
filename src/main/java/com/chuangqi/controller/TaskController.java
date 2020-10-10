package com.chuangqi.controller;

import com.chuangqi.entity.Task;
import com.chuangqi.entity.User;
import com.chuangqi.entity.UserTask;
import com.chuangqi.service.TaskService;
import com.chuangqi.tool.util.ResultUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/1/9
 * \* Time: 12:45
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Api(tags = "日程管理", description = "日程管理接口详细信息的描述")
@RestController
@RequestMapping(value = "/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 根据id删除日程
     */
    @ApiOperation(value = "根据taskId删除日程", notes = "返回字段说明:\n" +
            "数据库中日程")
    @ApiImplicitParam(name = "taskId", value = "日程id", dataType = "Integer", required = true, paramType = "form")
    @PostMapping(value = "/deleteTask")
    public ResultUtil deleteTask(Integer taskId){
        Boolean deleteResult = taskService.deleteTask(taskId);
        return ResultUtil.ok(deleteResult);
    }

    /**
     * 根据日程id来进行查询,获取的task对象与当前登录用户session中对比,是当前用户的返回到编辑页面
     */
    @ApiOperation(value = "根据taskId获取日程", notes = "返回字段说明:\n" +
            "数据库中日程")
    @ApiImplicitParam(name = "taskId", value = "日程id", dataType = "Integer", required = true, paramType = "form")
    @PostMapping(value = "/getTasksById")
    public ResultUtil getTaskbyId(Integer taskId , HttpServletRequest request){
        Task task = taskService.getTaskById(taskId);
//        if(task!=null){
//            Integer userId = task.getUserId();
//            User user = (User) request.getSession().getAttribute("user");
//            if(user!=null){
//                Integer id = user.getId();
//                if(id.equals(userId)){
//                    return ResultUtil.ok(task);
//                }
//            }
//        }
//        return ResultUtil.error(405,"当前日程不是属于你的");
        return ResultUtil.ok(task);
    }

    /**
     * 日程编辑修改
     */
    @PostMapping(value = "/updateTasks")
    public ResultUtil updateTask(Task task){
        Integer taskId = task.getTaskId();
        Boolean updateResult = taskService.updateTask(task);


        return  ResultUtil.ok(updateResult);
    }

    /**
     * 根据用户表和日程表的联合查询
     */
    @PostMapping(value = "/userTasks")
    public ResultUtil getAllUserTask (String beginTime, String endTime){
        List<UserTask> userTask = taskService.getAllUserTask(beginTime, endTime);
        return ResultUtil.ok(userTask);
    }



    /**
     * 根据用户来进行日程查询
     */
    @ApiOperation(value = "根据用户获取日程列表", notes = "返回字段说明:\n" +
            "List=根据用户数据库中日程列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beginTime", value = "日程开始时间", dataType = "String", required = true, paramType = "form"),
            @ApiImplicitParam(name = "endTime", value = "日程结束时间", dataType = "String", required = true, paramType = "form")
    })
    @PostMapping(value = "/userAndTasks")
    public ResultUtil getAllUserAndTask(String beginTime, String endTime) throws ParseException {
        List<UserTask> users = taskService.getAllUserAndTask(beginTime, endTime);


        return ResultUtil.ok(users);
    }

    /**
     * 分页获取日程列表
     * @return
     */
    @ApiOperation(value = "分页获取日程列表", notes = "返回字段说明:\n" +
            "List=数据库中日程列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页查询起始页", dataType = "int", required = true, paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "分页查询每页的个数", dataType = "int", required = true, paramType = "path")
    })
    @PostMapping(value = "/tasks/{pageNum}/{pageSize}")
    public ResultUtil getAllTasks(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize){
        //开启分页
        PageHelper.startPage(pageNum, pageSize);
        List<Task> tasks = taskService.getAllTasks();
        PageInfo<Task> pageInfo = new PageInfo<>(tasks);
        return ResultUtil.ok(pageInfo);
    }


    /**
     * 根据时间区间来查询对应日程
     */
    @ApiOperation(value = "根据时间获取日程列表", notes = "返回字段说明:\n" +
            "输入日期字符串格式为:2020-01-09 16:14:42\n"+
            "List=数据库中日程列表\n")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beginTime", value = "日程开始时间", dataType = "String", required = true, paramType = "form"),
            @ApiImplicitParam(name = "endTime", value = "日程结束时间", dataType = "String", required = true, paramType = "form")
    })
//    {beginTime}/{endTime}
    @PostMapping(value = "/task")
    public ResultUtil getTaskByBeginTime(String beginTime,String endTime ){
        List<Task> tasks = taskService.getTaskByBeginTime(beginTime, endTime);
        return ResultUtil.ok(tasks);
    }

    /**
     * 输入人员名称,模糊查询相对应的日程对象
     */
    @ApiOperation(value = "根据人员名称查询日程列表", notes = "返回字段说明:\n" +
            "输入日期字符串格式为:2020-01-09 16:14:42\n"+
            "List=对应人员名称的数据库中日程列表\n")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "人员名称", dataType = "String", required = true, paramType = "path"),
            @ApiImplicitParam(name = "beginTime", value = "日程开始时间", dataType = "String", required = true, paramType = "path"),
            @ApiImplicitParam(name = "endTime", value = "日程结束时间", dataType = "String", required = true, paramType = "path")
    })
    @PostMapping(value = "/taskByName/{name}/{beginTime}/{endTime}")
    public ResultUtil getTaskByName(@PathVariable("name")String name , @PathVariable("beginTime") String beginTime,@PathVariable("endTime") String endTime ){
        //将前端传来的name添加%%
        name = "%" + name + "%";
        List<Task> taskList = taskService.getTaskByName(name ,beginTime ,endTime);
        return ResultUtil.ok(taskList);
    }

    /**
     * 新增日程
     */
    @ApiOperation(value = "新增日程列表", notes = "返回字段说明:\n" +
            "是否新增成功\n")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "日程标题", dataType = "String", required = true, paramType = "path"),
            @ApiImplicitParam(name = "schedule", value = "日程详情", dataType = "String", required = true, paramType = "path"),
            @ApiImplicitParam(name = "beginTime", value = "日程开始时间", dataType = "String", required = true, paramType = "path"),
            @ApiImplicitParam(name = "endTime", value = "日程结束时间", dataType = "String", required = true, paramType = "path")
    })
    @PostMapping(value = "/addTask")
    public ResultUtil addTask(Task task , HttpServletRequest request){
        //日期格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createTime = new Date();
        String date = simpleDateFormat.format(createTime);
        task.setCreateTime(date);
        //从session中拿到当前用户
        User user = (User) request.getSession().getAttribute("user");
        Integer userId = user.getId();
        task.setUserId(userId);
        Boolean addResult = taskService.addTask(task);
        return ResultUtil.ok(addResult);
    }






}