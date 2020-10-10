package com.chuangqi.service.Impl;

import com.chuangqi.entity.Task;
import com.chuangqi.entity.User;
import com.chuangqi.entity.UserTask;
import com.chuangqi.mapper.TaskMapper;
import com.chuangqi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/1/9
 * \* Time: 13:47
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Resource
    private TaskMapper taskMapper;

    @Autowired
    private HttpServletRequest request;

    /**
     * 根据id来进行编辑日程
     */
    @Override
    public Boolean updateTask(Task task) {
        Integer i = taskMapper.updateTask(task);
        if (i != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据id来进行删除
     */
    @Override
    public Boolean deleteTask(Integer taskId) {
        Integer i = taskMapper.deleteTask(taskId);
        if (i != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据id来进行日程查询
     */
    @Override
    public Task getTaskById(Integer taskId) {
        return taskMapper.getTaskById(taskId);
    }

    /**
     * 新增日程
     */
    @Override
    public Boolean addTask(Task task) {
        Integer i = taskMapper.addTask(task);
        if (i != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据用户表和日程表的联合查询
     */
    @Override
    public List<UserTask> getAllUserTask(String beginTime, String endTime) {
        return taskMapper.getAllUserTask(beginTime, endTime);
    }

    /**
     * 根据用户来进行日程查询
     */
    @Override
    public List<UserTask> getAllUserAndTask(String beginTime, String endTime) throws ParseException {
        //日期格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //将传入的结束日期区间转为日期格式
        Date endDate = simpleDateFormat.parse(endTime);
        //将传入的开始日期区间转为日期格式
        Date beginDate = simpleDateFormat.parse(beginTime);
        //获取user集合,user中包含元素taskList为当前用户在日期区间的所有日程
        List<User> allUserAndTask = taskMapper.getAllUserAndTask();
        //新建UserTask集合
        List<UserTask> usertaskListNew = new ArrayList<>();
        //周二的区间
        Date beginDate2 = new Date(beginDate.getTime() + 24 * 3600 * 1000);
        //周三的区间
        Date beginDate3 = new Date(beginDate.getTime() + 2 * 24 * 3600 * 1000);
        //周四的区间
        Date beginDate4 = new Date(beginDate.getTime() + 3 * 24 * 3600 * 1000);
        //周五的区间
        Date beginDate5 = new Date(beginDate.getTime() + 4 * 24 * 3600 * 1000);
        //周六的区间
        Date beginDate6 = new Date(beginDate.getTime() + 5 * 24 * 3600 * 1000);
        //周日的区间
        Date beginDate7 = new Date(beginDate.getTime() + 6 * 24 * 3600 * 1000);
        //从当前登录用户中的session中获取
        User userOld = (User) request.getSession().getAttribute("user");

        //新建user集合
        List<User> userListNew = new ArrayList<>();
        //顺序加入新集合,创建一个标识
        int a = 1;
        //将user集合中的元素按日期拆解
        if(userOld!=null){
            System.out.println("登录了" + userOld);

            for (User user : allUserAndTask) {
                if (user.getId().equals(userOld.getId())) {
                    //新建一个UserTask对象用来接收这些属性
                    UserTask userTask = getUserTask(beginDate, endDate, beginDate2, beginDate3, beginDate4, beginDate5, beginDate6, beginDate7, user);
                    //将当前登录用户的日程放在集合第一个下标元素上
                    usertaskListNew.add(0, userTask);
                }else{
                    userListNew.add(user);
                }
            }
        }else{
            System.out.println("没登录");

            for (User user : allUserAndTask) {
                UserTask userTask = getUserTask(beginDate, endDate, beginDate2, beginDate3, beginDate4, beginDate5, beginDate6, beginDate7, user);


                //将当前登录用户的日程放在集合第一个下标元素上
                usertaskListNew.add(userTask);

            }
            return usertaskListNew;
        }



        //其他用户的日程
        //将user集合中的元素按日期拆解
        for (User user : userListNew) {
            UserTask userTask = getUserTask(beginDate, endDate, beginDate2, beginDate3, beginDate4, beginDate5, beginDate6, beginDate7, user);


            //将当前登录用户的日程放在集合第一个下标元素上
            usertaskListNew.add(a, userTask);
            a++;
        }


        return usertaskListNew;
    }

    private UserTask getUserTask(Date beginDate, Date endDate, Date beginDate2, Date beginDate3, Date beginDate4, Date beginDate5, Date beginDate6, Date beginDate7, User user) throws ParseException {
        //日期格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //新建一个UserTask对象用来接收这些属性
        UserTask userTask = new UserTask();
        //每个user对象对应的id
        Integer id = user.getId();
        userTask.setId(id);
        //每个user对象的名字
        String name = user.getName();
        userTask.setName(name);
        //每个user对象的工号
        String jobNumber = user.getJobNumber();
        userTask.setJobNumber(jobNumber);
        //每个user对象的头衔职位
        String position = user.getPosition();
        userTask.setPosition(position);

        //每个user对象的日程集合
        List<Task> taskList = user.getTaskList();
        if (taskList.size() != 0) {
            //新建7个集合
            List<Task> taskListNew1 = new ArrayList<>();
            List<Task> taskListNew2 = new ArrayList<>();
            List<Task> taskListNew3 = new ArrayList<>();
            List<Task> taskListNew4 = new ArrayList<>();
            List<Task> taskListNew5 = new ArrayList<>();
            List<Task> taskListNew6 = new ArrayList<>();
            List<Task> taskListNew7 = new ArrayList<>();

            for (Task task : taskList) {
                //user中的taskList中的task的日程开始时间
                String beginTime12 = task.getBeginTime();
                Date beginTime1 = simpleDateFormat.parse(beginTime12);
                //user中的taskList中的task的日程结束时间
                String endTime12 = task.getEndTime();
                if (beginTime1.compareTo(beginDate) >= 0 && beginTime1.compareTo(beginDate2) < 0) {
                    taskListNew1.add(task);
                } else if (beginTime1.compareTo(beginDate2) >= 0 && beginTime1.compareTo(beginDate3) < 0) {
                    taskListNew2.add(task);
                } else if (beginTime1.compareTo(beginDate3) >= 0 && beginTime1.compareTo(beginDate4) < 0) {
                    taskListNew3.add(task);
                } else if (beginTime1.compareTo(beginDate4) >= 0 && beginTime1.compareTo(beginDate5) < 0) {
                    taskListNew4.add(task);
                } else if (beginTime1.compareTo(beginDate5) >= 0 && beginTime1.compareTo(beginDate6) < 0) {
                    taskListNew5.add(task);
                } else if (beginTime1.compareTo(beginDate6) >= 0 && beginTime1.compareTo(beginDate7) < 0) {
                    taskListNew6.add(task);
                } else if (beginTime1.compareTo(beginDate7) >= 0 && beginTime1.compareTo(endDate) < 0) {
                    taskListNew7.add(task);
                }

            }
            //循环完毕之后,将新集合add到userTask对象中
            userTask.setTaskList1(taskListNew1);
            userTask.setTaskList2(taskListNew2);
            userTask.setTaskList3(taskListNew3);
            userTask.setTaskList4(taskListNew4);
            userTask.setTaskList5(taskListNew5);
            userTask.setTaskList6(taskListNew6);
            userTask.setTaskList7(taskListNew7);
        }
        return userTask;
    }

    /**
     * 根据用户来进行日程查询
     */

    @Override
    public List<Task> getAllTasks() {
        return taskMapper.getAllTasks();
    }

    @Override
    public List<Task> getTaskByBeginTime(String beginTime, String endTime) {
        return taskMapper.getTaskByBeginTime(beginTime, endTime);
    }

    /**
     * 根据日程的user_id字段,来进行用户名称的模糊查询,查询结果为模糊用户对应的日程对象,结果是多个对象,加入日期时间限制
     */
    @Override
    public List<Task> getTaskByName(String name, String beginTime, String endTime) {
        return taskMapper.getTaskByName(name, beginTime, endTime);
    }
}