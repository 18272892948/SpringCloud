package com.xwt.service;

import com.xwt.vo.ResultVo;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

    //登录
    ResultVo login(String username, String password, String ip);
    //校验
    ResultVo check(String token);
    //退出
    ResultVo exit(String token,String ip);


}
