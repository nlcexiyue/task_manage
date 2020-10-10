package com.chuangqi.mapper;

import com.chuangqi.entity.User;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    /**
     * 禁用的人员列表
     */
    @Select("select * from user where status = '2'")
    @Results(id = "userForbiddenResult" ,value = {
            @Result(property = "id" ,column = "id" ,id = true),
            @Result(property = "username" ,column = "username"),
            @Result(property = "password" ,column = "password"),
            @Result(property = "jobNumber" , column = "job_number"),
            @Result(property = "name" ,column = "name"),
            @Result(property = "position" , column = "position"),
            @Result(property = "sex" ,column = "sex"),
            @Result(property = "createTime" ,column = "create_time"),
            @Result(property = "email" ,column = "email"),
            @Result(property = "phone" ,column = "phone"),
            @Result(property = "status" ,column = "status"),
            @Result(property = "pictureUrl" ,column = "picture_url")
    })
    List<User> getAllForbiddenUser();

    /**
     * 用户注册
     */
    @Insert("insert into user ( username , password ,job_number ,  name , position ,  sex , create_time , email , phone , status ) " +
            "values (#{username} , #{password} ,#{jobNumber} ,  #{name} , #{position} , #{sex} , #{createTime} , #{email} , #{phone} , #{status} ) ")
    Integer userRegister(User user);

    /**
     * 修改用户密码
     */
    @Insert("update user set password = #{password} where id = #{id} ")
    Integer userModify(@Param("id") Integer id , @Param("password") String password);

    /**
     * 通过id来查询对应用户
     */
    @Select("select * from user where id = #{id}")
    @ResultMap(value = "userAndRoleResult")
    User getUserById(@Param("id") Integer id);

    /**
     * 通过用户登录名和用户登录密码来查询对象,以验证登录
     */
    @Select("select * from user where username = #{username} and password = #{password}")
    @ResultMap(value = "userAndRoleResult")
    User getUserByLogin(@Param("username") String username, @Param("password") String password);

    /**
     * 展示所有人员列表,其中多表联合包括,用户角色表对应的角色
     */
    @Select({"<script>",
            "select * from user",
            "<if test=\" name != '%%'  \">\twhere name like #{name}\n</if>",
            "</script>"})
    @Results(id = "userAndRoleResult" , value = {
            @Result(property = "id" ,column = "id" ,id = true),
            @Result(property = "username" ,column = "username"),
            @Result(property = "password" ,column = "password"),
            @Result(property = "jobNumber" , column = "job_number"),
            @Result(property = "name" ,column = "name"),
            @Result(property = "position" , column = "position"),
            @Result(property = "sex" ,column = "sex"),
            @Result(property = "createTime" ,column = "create_time"),
            @Result(property = "email" ,column = "email"),
            @Result(property = "phone" ,column = "phone"),
            @Result(property = "status" ,column = "status"),
            @Result(property = "pictureUrl" ,column = "picture_url"),
            @Result(property = "userRole" ,column = "id" ,one = @One(select = "com.chuangqi.mapper.UserRoleMapper.getRoleByUserId")),
    })
    List<User> getAllUserList(@Param("name") String name);

    /**
     * 用户上传头像图片
     */
    @Update("update user set picture_url = #{pictureUrl} where id = #{id}")
    Integer addUserPicture(@Param("pictureUrl") String pictureUrl , @Param("id") Integer id);

    /**
     * 用户禁用和恢复,禁用status=2,正常启动status=2
     */
    @Update("update user set status = #{status} where id = #{id}")
    Integer forbiddenUser(@Param("status") Integer status , @Param("id") Integer id);

    /**
     * 根据id来修改用户信息
     */
    @Update("update user set job_number = #{jobNumber} , name = #{name} , position = #{position} , " +
            "sex = #{sex} , email = #{email} , phone = #{phone} where id = #{id} ")
    Integer updateUserInfo(User user);



}
