package com.xwt.login.controller;

import com.xwt.login.provider.LoginProvider;
import com.xwt.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Api(value = "登录相关")
@RestController
public class LoginController {


    @Autowired
    private LoginProvider loginProvider;

    //登录
    @ApiOperation(value = "登录")
    @GetMapping("login.do")
    ResultVo login(String username, String password, HttpServletRequest request, HttpServletResponse response){

        return loginProvider.login(username, password, request, response);
    };
    //校验
    @ApiOperation(value = "验证登录有效")
    @GetMapping("check.do")
    ResultVo check(HttpServletRequest request){
        return loginProvider.check(request);
    };
    //退出
    @ApiOperation(value = "退出")
    @GetMapping("exit.do")
    ResultVo exit(HttpServletRequest request, HttpServletResponse response){
        return loginProvider.exit(request, response);
    };

}
