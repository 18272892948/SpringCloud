package com.xwt.dao;

import com.xwt.entity.DbRedis;

public interface DbRedisMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DbRedis record);

    int insertSelective(DbRedis record);

    DbRedis selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DbRedis record);

    int updateByPrimaryKey(DbRedis record);

    DbRedis selectByToken(String token);



}