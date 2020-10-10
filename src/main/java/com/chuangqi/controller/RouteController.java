package com.chuangqi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 路由类
 */
@Controller
public class RouteController {

    @RequestMapping("/index") // 请求地址index
    public String index(){
        return "index"; // templates目录下的index页面
    }
    @RequestMapping("/a") // 请求地址index
    public String a(){
        return "indexTest"; // templates目录下的index页面
    }
    @RequestMapping("/login") // 请求地址index
    public String login(){
        return "login"; // templates目录下的index页面
    }
    @RequestMapping("/personal/myself") // 请求地址index
    public String personalMyInfo(){
        return "personal/myself"; // templates目录下的index页面
    }
    @RequestMapping("/personal/newManage") // 请求地址index
    public String personalNewManage(){
        return "personal/newManage"; // templates目录下的index页面
    }
    @RequestMapping("/personal/roomManage") // 请求地址index
    public String personalRoomManage(){
        return "personal/roomManage"; // templates目录下的index页面
    }
    @RequestMapping("/personal/personManage") // 请求地址index
    public String personalPersonManage(){
        return "personal/personManage"; // templates目录下的index页面
    }
    @RequestMapping("/personal/addPerson") // 请求地址index
    public String personalAddPerson(){
        return "personal/addPerson"; // templates目录下的index页面
    }
    @RequestMapping("/personal/updatePerson") // 请求地址index
    public String personalUpdatePerson(){
        return "personal/updatePerson"; // templates目录下的index页面
    }
    @RequestMapping("/personal/newManage/editNew") // 请求地址index
    public String personalNewManageEdit(){
        return "editor/editorNew"; // templates目录下的index页面
    }
    @RequestMapping("/personal/newManage/editRoom") // 请求地址index
    public String personalNewManageEditRoom(){
        return "editor/editorRoom"; // templates目录下的index页面
    }
    @RequestMapping("/personal/newManage/editMessage") // 请求地址index
    public String personalNewManageEditMessage(){
        return "editor/editorMessage"; // templates目录下的index页面
    }
    @RequestMapping("/personal/message") // 请求地址index
    public String personalMessage(){
        return "personal/message_test1"; // templates目录下的index页面
    }
    @RequestMapping("/personal/imgs") // 请求地址index
    public String personalImgs(){
        return "personal/imgs"; // templates目录下的index页面
    }
    @RequestMapping("/documentManage") // 请求地址index
    public String documentManage(){
        return "frontPages/documentManage"; // templates目录下的index页面
    }
    @RequestMapping("/news") // 请求地址index
    public String news(){
        return "frontPages/news"; // templates目录下的index页面
    }
    @RequestMapping("/aboutUs") // 请求地址index
    public String aboutUs(){
        return "frontPages/aboutUs"; // templates目录下的index页面
    }
    @RequestMapping("/newsDetail") // 请求地址index
    public String newsDetail(){
        return "frontPages/newsDetail"; // templates目录下的index页面
    }
    @RequestMapping("/messageDetail") // 请求地址index
    public String messageDetail(){
        return "frontPages/messageDetail"; // templates目录下的index页面
    }


}
