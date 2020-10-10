package com.chuangqi.controller;

import com.chuangqi.entity.*;
import com.chuangqi.service.RolePermissionsService;
import com.chuangqi.service.UserRoleService;
import com.chuangqi.service.UserService;
import com.chuangqi.tool.util.ResultUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/1/13
 * \* Time: 9:31
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Api(tags = "用户人员管理", description = "用户人员管理接口详细信息的描述")
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RolePermissionsService rolePermissionsService;



    /**
     * 用户上传的照片,就放在这里
     */
    @Value("${save.pic}")
    private String uploadPath;
    @Value("${tmp.rootTemp}")
    private String rootTempPath;
    @Value("${nginx.url}")
    private String nginxUrl;


    /**
     * 禁用的用户列表
     */
    @ApiOperation(value = "禁用的人员列表展示", notes = "返回字段说明:\n" +
            "List所有禁用人员展示角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页查询起始页", dataType = "int", required = true, paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "分页查询每页的个数", dataType = "int", required = true, paramType = "path")
    })
    @PostMapping(value = "/userForbiddenList")
    public ResultUtil getAllForbiddenUser(Integer pageNum,  Integer pageSize ){
        //开启分页
        PageHelper.startPage(pageNum, pageSize);

        List<User> forbiddenUser = userService.getAllForbiddenUser();
        PageInfo<User> pageInfo = new PageInfo<>(forbiddenUser);
        return ResultUtil.ok(pageInfo);



    }

    /**
     * 用户注册,检验用户名是否重复
     */
    @ApiOperation(value = "检验用户名是否重复", notes = "返回字段说明:\n" +
            "是否修改用户密码成功")
    @PostMapping(value = "/checkoutUsername")
    public ResultUtil checkoutUsername(String username){
        if(username!=null){
            List<User> userList = userService.getAllUserList("%%");
            for (User user : userList) {
                if(username.equals(user.getUsername())){
                    return ResultUtil.error(300,"用户名重复");
                }
            }

            return ResultUtil.ok("用户名可以使用");
        }
        return ResultUtil.error(301,"用户名不能为空");

    }



    /**
     * 用户注册,想定是在管理员的权限下才可以创建用户,先写出基本的注册用户方法
     *
     * 用户在注册界面,可以输入用户登录名,用户登录密码,用户名称,用户性别,用户邮箱,用户电话,用户状态,
     *             可以上传用户照片
     */
    @ApiOperation(value = "注册用户", notes = "返回字段说明:\n" +
            "可以输入用户登录名value=username\n"+
            "用户登录密码value=password\n"+
            "用户工号value=jobNumber\n"+
            "用户名称value=name\n"+
            "用户头衔value=position\n"+
            "用户性别value=sex,数值0和1\n"+
            "用户邮箱value=email\n"+
            "用户电话value=phone\n"+
            "用户状态value=status,数值1:正常用户,数值2:禁用用户\n"+
            "用户角色value=roleId\n"+
            "Boolean=是否注册成功")
    @PostMapping(value = "/user")
    public ResultUtil userRegister(User user , Integer roleId ){
        String username = user.getUsername();
        if(username!=null && username!=""){
            List<User> allUserList = userService.getAllUserList("%%");
            for (User user1 : allUserList) {
                if(username.equals(user1.getUsername())){
                    return ResultUtil.error(300,"用户名重复,注册失败");
                }
            }
        }
        //调用用户图片上传方法
        user.setCreateTime(new Date());
        Boolean uploadResult = userService.userRegister(user);
        //添加用户到角色的标识
        Boolean addResult = false;
        if(uploadResult){
            //用户信息新增表中成功
            User userByLogin = userService.getUserByLogin(user.getUsername(), user.getPassword());
            //查询出来这个id
            Integer userId = userByLogin.getId();
            addResult = userRoleService.addUserRole(userId, roleId);
        }


        return ResultUtil.ok(addResult);
    }

    /**
     * 修改用户密码,从修改表单中将旧密码和新密码输入
     */
    @ApiOperation(value = "修改用户密码", notes = "返回字段说明:\n" +
            "请求的字段旧密码value:oldPassword\n" +
            "请求的字段新密码value:newPassword\n" +
            "是否修改用户密码成功")
    @PostMapping(value = "/modifyUser")
    public ResultUtil userModify(String oldPassword , String newPassword , HttpServletRequest request){
        //从session域中取出当前登录用户的信息
        User bean = (User)request.getSession().getAttribute("user");
        //当前用户id
        Integer id = bean.getId();
        //当前用户登录名
        String username = bean.getUsername();
        //核对用户名和旧密码
        User user = userService.getUserByLogin(username, oldPassword);
        if(user!=null){
            Boolean modifyResult = userService.userModify(id, newPassword);
            if(modifyResult){
                return ResultUtil.ok(modifyResult);
            }else {
                return ResultUtil.error(300,"用户登录密码更改失败,请重新登录");
            }
        }else {
            return ResultUtil.error(301,"当前旧密码输入错误,请重新输入");
        }
    }

    /**
     * 用户登录,从表单中将用户名和密码请求过来
     * 将用户信息添加到session域中
     */
    @ApiOperation(value = "用户登录", notes = "返回字段说明:\n" +
            "请求的字段用户登录名value:username\n" +
            "请求的字段密码value:password\n" +
            "是否用户登录成功")
    @PostMapping(value = "/userLogin")
    public ResultUtil userLogin(HttpServletRequest request , User user) throws IOException {
//        session.setAttribute("aa", "aa");
//        System.out.println("aa:----"+session.getAttribute("aa"));
        String username = user.getUsername();
        String password = user.getPassword();
        User userNew = userService.getUserByLogin(username, password);

        //登录成功后,路由拦截的标识
        Boolean landing = false;

        if(userNew!=null){
            Integer status = userNew.getStatus();
            if(status==1){
                //用户登录成功后,生成一个uuid,放在session中,新闻模块的富文本编辑器需要上传图片,即时返回存储路径的pictureUrl,以这个uuid作为存储的文件夹
                String uuid = UUID.randomUUID().toString();
                request.getSession().setAttribute("uuid",uuid);
                request.getSession().setAttribute("user",userNew);

                User bean = (User)request.getSession().getAttribute("user");
                String uuid1 = (String) request.getSession().getAttribute("uuid");
                System.out.println(uuid1);

                System.out.println(bean.getUsername()+"----登陆了");
                Integer id = bean.getId();
                //查询当前用户对应的角色
                UserRole userRole = userRoleService.getUserRoleByUserId(id);
                //当前用户对应的角色id
                Integer roleId = userRole.getRoleId();
                //根据角色id查询到的权限列表
                List<RolePermissions> rolePermissionsList = rolePermissionsService.getRolePermissionsByRoleId(roleId);
                Map<String , Object> map = new HashMap<>();
                //登录成功后,路由拦截的标识
                landing = true;
                map.put("user",bean);
                map.put("permissions",rolePermissionsList);
                map.put("landing" , landing);
                System.out.println("发送到前端的信息" + map);
//            map.put("picture" ,picture);
                return ResultUtil.ok(map);
            }else{
                return ResultUtil.error(406,"用户已禁用");
            }

        }else{
            return ResultUtil.error(400,"登录名或者密码错误");
        }
    }

    /**
     * 用户登出
     */
    @ApiOperation(value = "用户登出", notes = "返回字段说明:\n")
    @PostMapping(value = "/userLoginOut")
    public ResultUtil userLoginOut(HttpServletRequest request){
        request.getSession().removeAttribute("user");
//        request.getSession().invalidate();
        User user = (User) request.getSession().getAttribute("user");
        System.out.println("登出的用户是:" + user);
        return ResultUtil.ok("用户登出");
    }


    /**
     * 界面初始化的时候,要向前端页面传递一个数组,这个数组中的元素,就是前端页面的关于权限功能的展示
     */
    @ApiOperation(value = "用户权限功能列表展示", notes = "返回字段说明:\n" +
            "List当前登录用户对应的角色所具有的权限")
    @GetMapping(value = "/userPermission")
    public ResultUtil userPermissionInit(HttpServletRequest request){
        User bean = (User)request.getSession().getAttribute("user");
        //从session域中拿到用户id
        Integer userId = bean.getId();

        //查询当前用户对应的角色
        UserRole userRole = userRoleService.getUserRoleByUserId(userId);
        //当前用户对应的角色id
        Integer roleId = userRole.getRoleId();
        //根据角色id查询到的权限列表
        List<RolePermissions> rolePermissionsList = rolePermissionsService.getRolePermissionsByRoleId(roleId);
        return ResultUtil.ok(userRole);
    }

    /**
     * 所有人员列表的展示
     */
    @ApiOperation(value = "人员列表展示", notes = "返回字段说明:\n" +
            "List所有人员展示角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页查询起始页", dataType = "int", required = true, paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "分页查询每页的个数", dataType = "int", required = true, paramType = "path")
    })
    @PostMapping(value = "/userList")
    public ResultUtil getAllUserList(Integer page,  Integer limit , String name , Integer roleId){
        //根据name的模糊查询
        name = "%" + name + "%";
        //开启分页
        PageHelper.startPage(page, limit);
        List<User> allUserList = userService.getAllUserList(name);
        //新建存储集合
        List<User> allUserListStatus = new ArrayList<>();
        //判定用户状态,status的状态,数值1为正常状态用户,数值2为禁用状态用户
        for (User user : allUserList) {
            Integer status = user.getStatus();
            if(status==1){
                if(roleId!=-1){
                    Integer roleIdUser = user.getUserRole().getRoleId();
                    if(roleId.equals(roleIdUser)){
                        allUserListStatus.add(user);
                    }
                }else{
                    allUserListStatus.add(user);
                }

            }

        }

        PageInfo<User> pageInfo = new PageInfo<>(allUserList);
        pageInfo.setList(allUserListStatus);
        return ResultUtil.ok(0,pageInfo);
    }

    /**
     * 为用户分配角色
     */
    @ApiOperation(value = "人员分配角色", notes = "返回字段说明:\n" +
            "是否分配")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "int", required = true, paramType = "path"),
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "int", required = true, paramType = "path")
    })
    @PostMapping(value = "/userAndRole/{userId}/{roleId}")
    public ResultUtil addUserRole(@PathVariable("userId")Integer userId ,@PathVariable("roleId")Integer roleId){
        Boolean addResult = userRoleService.addUserRole(userId, roleId);
        return ResultUtil.ok(addResult);
    }

    /**
     * 删除用户,其实不是真的删除,是把用户状态改为禁用
     */
    @ApiOperation(value = "用户删除禁用", notes = "返回字段说明:\n" +
            "是否删除禁用成功")
    @ApiImplicitParam(name = "id", value = "用户id", dataType = "int", required = true, paramType = "path")
    @PostMapping(value = "/forbiddenUser")
    public ResultUtil forbiddenUser(Integer id){
        User user = userService.getUserById(id);
        Integer status = user.getStatus();
        Boolean updateResult = false;
        if(status==1){
            updateResult = userService.forbiddenUser(2, id);

        }

        return ResultUtil.ok(updateResult);
    }

    /**
     *
     */
    @ApiOperation(value = "根据id查询个人信息", notes = "返回字段说明:\n" +
            "user对象")
    @PostMapping(value = "/getUserById")
    public ResultUtil getUserById(Integer id){
        User user = userService.getUserById(id);

        return ResultUtil.ok(user);
    }

    /**
     * 用户更改信息
     */
    @ApiOperation(value = "修改个人信息", notes = "返回字段说明:\n" +
            "是否修改成功")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要修改的用户id", dataType = "int", required = true, paramType = "path"),
            @ApiImplicitParam(name = "jobNumber", value = "用户工号", dataType = "String", required = true, paramType = "path"),
            @ApiImplicitParam(name = "name", value = "用户名字", dataType = "String", required = true, paramType = "path"),
            @ApiImplicitParam(name = "position", value = "用户头衔", dataType = "String", required = true, paramType = "path"),
            @ApiImplicitParam(name = "sex", value = "用户性别", dataType = "String", required = true, paramType = "path"),
            @ApiImplicitParam(name = "email", value = "用户邮箱", dataType = "String", required = true, paramType = "path"),
            @ApiImplicitParam(name = "phone", value = "用户电话", dataType = "String", required = true, paramType = "path"),
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "int", required = true, paramType = "path")
    })
    @Transactional
    @PostMapping(value = "/updateUserInfo")
    public ResultUtil updateUserInfo(User user , Integer roleId){

        //修改用户表的标识
        Boolean updateUserResult = false;
        //修改角色表的标识
        Boolean updateRoleResult = false;
        if(user!=null){
            //获取user的id
            Integer userId = user.getId();
            updateRoleResult = userRoleService.updateUserRole(roleId,userId);
            updateUserResult = userService.updateUserInfo(user);

        }

        return ResultUtil.ok(updateUserResult && updateRoleResult);
    }


    /**
     * 管理员重置密码
     */
    @ApiOperation(value = "重置密码", notes = "返回字段说明:\n" +
            "是否重置成功")
    @PostMapping(value = "/userReset")
    public ResultUtil userReset(Integer id){
        Boolean updateResult = userService.userModify(id, "123456");
        return ResultUtil.ok(updateResult);
    }

    /**
     * 用户自己修改上传头像
     */
    @ApiOperation(value = "用户自己上传修改头像", notes = "返回字段说明:\n" +
            "是否上传成功")
    @PostMapping(value = "/userPicture")
    public ResultUtil addUserPicture( HttpServletRequest request){
        //登录后才能上传

        User user = (User) request.getSession().getAttribute("user");
        System.out.println("----" + user);

        if(user!=null){
            Integer id = user.getId();
            String pictureUrl = uploadPicture(request);
            pictureUrl = nginxUrl + pictureUrl.substring(uploadPath.length());
            Boolean addResult = userService.addUserPicture(pictureUrl, id);
            if(addResult){
                return ResultUtil.ok(pictureUrl);
            }else{
                return ResultUtil.ok(addResult);
            }

        }else{
            return ResultUtil.error(405,"用户没有登录");
        }


    }

    /**
     * 用户上传照片的存储方法,返回上传成功的图片文件的本地存储路径
     */
    private String uploadPicture(HttpServletRequest request){
        MultipartHttpServletRequest multipartHttpServletRequest =null;
        if (request instanceof MultipartHttpServletRequest) {
            multipartHttpServletRequest = (MultipartHttpServletRequest)(request);
            MultipartFile multipartFile = multipartHttpServletRequest.getFile("file");
            if(multipartFile!=null){
                //获取上传文件的文件名称
                String pictureName = multipartFile.getOriginalFilename();
                pictureName = pictureName.substring(pictureName.lastIndexOf("\\")+1);
                //文件名可能在存储空间中重复，后面加一个uuid
                String uuid = UUID.randomUUID().toString();
                //获取上传文件的文件类型
                String type = multipartFile.getContentType();

                //获取上传文件的文件大小
                Integer fileSize = (int)multipartFile.getSize();
                System.out.println(type);
                //上传的图片限制大小不超过5MB
                if(fileSize < 1024 * 1024 * 5){

                    if("image/jpg".equals(type) || "image/jpeg".equals(type) || "image/png".equals(type)){
                        String folderUrl = uploadPath + "/userPicture";
                        File folderFile = new File(folderUrl);
                        if(!folderFile.exists()){
                            folderFile.mkdirs();
                        }
                        String uploadUrl = folderUrl + "/" + uuid + "&" + pictureName;
                        File uploadFile = new File(uploadUrl);
                        try {
                            multipartFile.transferTo(uploadFile);
                            return uploadUrl;
                        } catch (IOException e) {
                            throw new RuntimeException("用户照片上传出问题了");
                        }
                    }else {
                        throw new RuntimeException("用户照片上传的格式仅支持jpg,jpeg,png,请确认图片格式");
                    }
                }else {
                    throw new RuntimeException("用户照片大小不符要求");
                }
            }else{
                throw new RuntimeException("上传的图片为空");
            }
        }
        throw new RuntimeException("上传的图片为空");

    }




}