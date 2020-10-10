package com.chuangqi.controller;

import com.chuangqi.tool.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: xiyue
 * \* Date: 2020/1/14
 * \* Time: 9:42
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Api(tags = "IP管理", description = "IP管理接口详细信息的描述")
@RestController
public class IPController {

    @Value("${ip}")
    private String ip;

    @Value("${server.port}")
    private String port;

    @ApiOperation(value = "获取ip和端口", notes = "返回字段说明:\n" +
            "ip和端口")
    @GetMapping(value = "/ip")
    public ResultUtil getServerIP(){
        String address = "http://" + ip + ":" + port;
        return ResultUtil.ok(address);
    }
}