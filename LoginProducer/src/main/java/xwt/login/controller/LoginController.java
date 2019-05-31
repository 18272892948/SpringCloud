package xwt.login.controller;


import com.xwt.service.LoginService;
import com.xwt.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    //登录
    @GetMapping("login/login.do")
    public ResultVo login(String username, String password, String ip){
        return loginService.login(username, password, ip);
    };
    //校验
    @GetMapping("login/check.do")
    public   ResultVo check(String token){
        return loginService.check(token);
    };
    //退出
    @GetMapping("login/exit.do")
    public  ResultVo exit(String token, String ip){
        return loginService.exit(token,ip);
    };




}
