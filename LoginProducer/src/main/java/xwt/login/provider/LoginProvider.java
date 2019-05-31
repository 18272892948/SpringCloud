package xwt.login.provider;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xwt.cofing.SystemConst;
import com.xwt.dao.DbRedisMapper;
import com.xwt.dao.DbUserLogMapper;
import com.xwt.dao.DbUserMapper;
import com.xwt.entity.DbRedis;
import com.xwt.entity.DbUser;
import com.xwt.entity.DbUserLog;
import com.xwt.service.LoginService;
import com.xwt.util.JedisUtil;
import com.xwt.util.PassUtil;
import com.xwt.util.ResultUtil;
import com.xwt.util.TokenUtil;
import com.xwt.vo.ResultVo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;


@Service
public class LoginProvider implements LoginService {
    @Autowired
    private DbUserMapper dbUserMapper;
    @Autowired
    private DbUserLogMapper dbUserLogMapper;

    @Autowired
    private DbRedisMapper dbRedisMapper;


    @Override
    public ResultVo login(String username, String password, String ip) {

        //查询用户是否存在
        DbUser dbUser = dbUserMapper.selectByUsername(username);
        if (dbUser!=null){
            //验证用户是否密码正确
            if(dbUser.getPassword().equals(password)){
                //生成token
                String token = TokenUtil.createToken(dbUser.getId(), username);
               //4、存储到Redis中
                //jedisUtil.save(SystemConst.PHONETOKEN,username,token);
                //jedisUtil.save(SystemConst.LOGINTOKEN,token, JSON.toJSONString(dbUser));


                DbRedis dbRedis=new DbRedis();
                dbRedis.setToken(token);
                dbRedis.setUsername(username);
                dbRedisMapper.insert(dbRedis);


                //添加用户登录信息
                DbUserLog userlog=new DbUserLog();
                userlog.setCreatetime(new Date());
                userlog.setOperation("登录用户"+token);
                 userlog.setUid(dbUser.getId());
                userlog.setIp(ip);
                dbUserLogMapper.insert(userlog);

                //5、返回信息
                return ResultUtil.exec(true,"OK",token);

            }
        }
        return ResultUtil.exec(false,"用户名或密码不正确",null);
    }
//验证token是否有效
    @Override
    public ResultVo check(String token) {

        if (null!=token){
            DbRedis dbRedis = dbRedisMapper.selectByToken(token);
            if(dbRedis!=null){
                return ResultUtil.exec(true,"OK",dbRedis);
            }
        }

     /*   if(jedisUtil.isHave(SystemConst.LOGINTOKEN,token)){
            return ResultUtil.exec(true,"OK",JSON.parseObject(jedisUtil.get(SystemConst.LOGINTOKEN,token),User.class));
        }*/
        return ResultUtil.exec(false,"登录失效",null);
    }

    @Override
    public ResultVo exit(String token, String ip) {

      /*  if(jedisUtil.isHave(SystemConst.LOGINTOKEN,token)){
            jedisUtil.del(SystemConst.LOGINTOKEN,token);
            String msg= PassUtil.base64Dec(token,"UTF-8");
            String[] arr=msg.split(",");
            jedisUtil.del(SystemConst.PHONETOKEN,arr[1]);
            DbUserLog userlog=new DbUserLog();
            userlog.setOperation("用户退出");
            userlog.setCreatetime(new Date());
            userlog.setUid(Integer.parseInt(arr[0]));
            userlog.setIp(ip);
            dbUserLogMapper.insert(userlog);
        }*/


        if (null!=token){


            String msg= PassUtil.base64Dec(token,"UTF-8");
            String[] arr=msg.split(",");
            DbRedis dbRedis = dbRedisMapper.selectByToken(token);
            if(dbRedis!=null){

                Integer i = dbRedisMapper.deleteByToken(token);
                if (i==0){
                    System.out.println(i);
                    DbUserLog userlog=new DbUserLog();
                    userlog.setOperation("自动失效推出");
                    userlog.setCreatetime(new Date());
                    userlog.setUid(Integer.parseInt(arr[0]));
                    userlog.setIp(ip);
                    dbUserLogMapper.insert(userlog);

                }else{
                    System.out.println(i);
                    DbUserLog userlog=new DbUserLog();
                    userlog.setOperation("用户退出");
                    userlog.setCreatetime(new Date());
                    userlog.setUid(Integer.parseInt(arr[0]));
                    userlog.setIp(ip);
                    dbUserLogMapper.insert(userlog);
                }





            }else{
                System.out.println("用户失效");
            }
        }
        return ResultUtil.exec(true,"OK",null);
    }
}
