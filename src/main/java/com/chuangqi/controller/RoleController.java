package com.chuangqi.controller;

import com.chuangqi.entity.Role;
import com.chuangqi.mapper.RoleMapper;
import com.chuangqi.service.RolePermissionsService;
import com.chuangqi.service.RoleService;
import com.chuangqi.tool.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/1/13
 * \* Time: 13:47
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Api(tags = "用户角色管理", description = "用户角色管理接口详细信息的描述")
@RestController
@RequestMapping(value = "/role")
public class RoleController {

    @Resource
    private RolePermissionsService rolePermissionsService;

    @Resource
    private RoleService roleService;

    @ApiOperation(value = "新增用户角色", notes = "返回字段说明:\n" +
            "新增用户角色名称value=name\n"+
            "新增用户角色描述value=description"+
            "是否新增成功")
    @PostMapping(value = "/role")
    public ResultUtil addRole(HttpServletRequest request){
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Boolean addResult = roleService.addRole(name, description);
        return ResultUtil.ok(addResult);
    }

    /**
     * 给角色分配权限,在单独的界面中,角色管理界面,分配角色拥有的权限
     */

    @ApiOperation(value = "角色分配权限", notes = "返回字段说明:\n" +
            "在单独的界面中,角色管理界面,分配角色拥有的权限\n" +
            "是否分配")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "int", required = true, paramType = "path"),
            @ApiImplicitParam(name = "permissionsId", value = "权限id", dataType = "int", required = true, paramType = "path")
    })
    @PostMapping(value = "/roleAndPermissions/{roleId}/{permissionsId}")
    public ResultUtil addRolePermissions(@PathVariable("roleId")Integer roleId , @PathVariable("permissionsId")Integer permissionsId){
        Boolean addResult = rolePermissionsService.addRolePermissions(roleId, permissionsId);
        return ResultUtil.ok(addResult);
    }

    /**
     * 查询全部角色
     */
    @ApiOperation(value = "查询全部角色", notes = "返回字段说明:\n" +
            "是否分配")
    @PostMapping(value = "/roles")
    public ResultUtil getAllRoles(){
        List<Role> roles = roleService.getAllRoles();
        return ResultUtil.ok(roles);

    }








}