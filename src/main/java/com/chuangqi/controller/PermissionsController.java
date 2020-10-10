package com.chuangqi.controller;

import com.chuangqi.entity.Permissions;
import com.chuangqi.service.PermissionsService;
import com.chuangqi.tool.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/1/13
 * \* Time: 14:29
 * \* To change this template use File | Settings | File Templates.
 * \* Description:权限表
 * \
 */
@Api(tags = "用户权限管理", description = "用户权限管理接口详细信息的描述")
@RestController
@RequestMapping(value = "/permissions")
public class PermissionsController {

    @Resource
    private PermissionsService permissionsService;

    @ApiOperation(value = "所有权限功能列表展示", notes = "返回字段说明:\n" +
            "List所有权限功能")
    @GetMapping(value = "/allPermissions")
    public ResultUtil getAllPermissions(){
        List<Permissions> list = permissionsService.getAllPermissions();
        return ResultUtil.ok(list);
    }









}