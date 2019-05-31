package com.xwt.login.provider;


import com.xwt.cofing.SystemConst;
import com.xwt.util.ResultUtil;
import com.xwt.vo.GetIp;
import com.xwt.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class LoginProvider {

    @Autowired
    private RestTemplate restTemplate;

    private GetIp getIp;


    //登录
    public ResultVo login(String username, String pass, HttpServletRequest request, HttpServletResponse response) {


        String ipAddr = getIp.getIpAddr(request);
        Cookie[] cks = request.getCookies();
        String token = "";
        ResultVo resultVo = null;
       if(cks!=null){
           for (Cookie c : cks) {
               if (c.getName().equals(SystemConst.COOKIETOKEN)) {
                   token = c.getValue();
                   break;
               }
           }
       }

        if (token.length() > 0) {
            /*  resultVo=loginService.check(token);*/
            resultVo = restTemplate.getForObject("http://loginprovider/login/check.do?token=" + token, ResultVo.class);
        } else {
            //没有过Token
            /*   resultVo=loginService.login(phone,pass,request.getRemoteAddr());*/
            resultVo = restTemplate.getForObject("http://loginprovider/login/login.do?username=" + username + "&password=" + pass + "&ip=" + ipAddr, ResultVo.class);
            if (resultVo.getCode() == 1) {
                Cookie cookie = new Cookie(SystemConst.COOKIETOKEN, resultVo.getData().toString());
                cookie.setPath("/");
                cookie.setDomain("");
                response.addCookie(cookie);
            }
        }
        return resultVo;
    }

    //注销接口
    public ResultVo exit(HttpServletRequest request, HttpServletResponse response) {
        String ipAddr = getIp.getIpAddr(request);
        Cookie[] cks = request.getCookies();
        String token = "";
        ResultVo resultVo = null;
        if(cks!=null){
            for (Cookie c : cks) {
                if (c.getName().equals(SystemConst.COOKIETOKEN)) {
                    token = c.getValue();
                    break;
                }
            }
        }
        if (token.length() > 0) {
            //删除Redis
            //删除Cookie
            /*loginService.exit(token);*/
            restTemplate.getForObject("http://loginprovider/login/exit.do?token=" + token+"&ip="+ipAddr, ResultVo.class);
          /*  Cookie cookie = new Cookie(SystemConst.COOKIETOKEN, "");
            cookie.setMaxAge(0);
            response.addCookie(cookie);*/
        }
        return ResultUtil.exec(true, "OK", null);
    }

    //校验是否登录有效性
    public ResultVo check(HttpServletRequest request) {
        Cookie[] cks = request.getCookies();
        String token = "";
        ResultVo resultVo = null;
        if(cks!=null){
            for (Cookie c : cks) {
                if (c.getName().equals(SystemConst.COOKIETOKEN)) {
                    token = c.getValue();
                    break;
                }
            }
        }

        if (token.length() > 0) {
            /*  resultVo=loginService.check(token);*/
            resultVo = restTemplate.getForObject("http://loginprovider/login/check.do?token=" + token, ResultVo.class);
        } else {
            resultVo = ResultUtil.exec(false, "登录无效", null);
        }
        return resultVo;
    }
}