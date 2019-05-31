package com.xwt.util;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 *@Author feri
 *@Date Created in 2019/3/19 14:39
 */
public class JedisUtil {
    private JedisPool jedisPool;
    private Jedis jedis;



    public JedisUtil(String host, int port, String pass) {
        jedisPool=new JedisPool(host,port);
        jedis=jedisPool.getResource();
        jedis.auth(pass);
    }
    public boolean isHave(String key,String field){
       return jedis.hexists(key,field);
    }
    //常用操作
    //存储
    public String save(String key,String value){
        return jedis.set(key,value);
    }
    public long save(String key,Map<String,String> map){
        return jedis.hset(key,map);
    }
    public long save(String key,String field,String value){
        return jedis.hset(key,field,value);
    }
    public long save(String key,String... list){
        return jedis.lpush(key,list);
    }
    public long saveSet(String key,String[] set){
        return jedis.sadd(key,set);
    }
    public long saveZSet(String key,Map<String,Double> map){
        return jedis.zadd(key,map);
    }

    //查询
    public String get(String key){
        return jedis.get(key);
    }
    public String get(String key,String field){
        return jedis.hget(key,field);
    }

    //删除
    public long del(String key){
        return jedis.del(key);
    }
    public long del(String key,String field){
        return jedis.hdel(key,field);
    }
    //校验
    public boolean exists(String key){
        return jedis.exists(key);
    }
    //设置有效期
    public long expire(String key,int seconds){
        return jedis.expire(key,seconds);
    }


}
