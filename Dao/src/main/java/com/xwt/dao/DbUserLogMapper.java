package com.xwt.dao;


import com.xwt.entity.DbUserLog;

public interface DbUserLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DbUserLog record);

    int insertSelective(DbUserLog record);

    DbUserLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DbUserLog record);

    int updateByPrimaryKey(DbUserLog record);
}