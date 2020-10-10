package com.chuangqi.config;

import com.chuangqi.entity.User;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/1/9
 * \* Time: 16:10
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class boot {
    public static void main(String[] args) throws Exception {

        Connection con = null;
        PreparedStatement ps = null;
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://192.168.2.177:3306/task_manage?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
        con = DriverManager.getConnection(url, "root", "root");

        for (int i = 0; i < 100 ; i++) {
            String sql = "insert into notice (user_id , title , article , create_time ,update_time) values (?,?,?,?,?)";
            ps = con.prepareStatement(sql);

            java.util.Date date = new java.util.Date();
            long l = date.getTime();

            java.sql.Date sqlDate1 = new java.sql.Date(l);
            //随机生成的字符串,60个字符
            String titleName = getRandomName60();
            //随机生成的字符串,600个字符
            String articleName = getRandomName600();

            ps.setInt(1,1);
            ps.setString(2,titleName);
            ps.setString(3,articleName);
            ps.setDate(4,sqlDate1);
            ps.setDate(5,sqlDate1);
            ps.executeUpdate();

            System.out.println("存储数据"+i+"成功");


        }
        con.close();
        ps.close();








    }


    public static String getRandomName60() {
        String taskName = "";
        for (int j = 0; j < 3; j++) {
            String s = RandomStringUtils.random(20, 0x4e00, 0x9fa5, false, false);
            taskName = taskName + s;
        }
        return taskName;
    }
    public static String getRandomName600() {
        String taskName = "";
        for (int j = 0; j < 3; j++) {
            String s = RandomStringUtils.random(200, 0x4e00, 0x9fa5, false, false);
            taskName = taskName + s;
        }
        return taskName;
    }
}