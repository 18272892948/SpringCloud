package com.xwt.dao;

import com.xwt.entity.DbUser;

public interface DbUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DbUser record);

    int insertSelective(DbUser record);

    DbUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DbUser record);

    int updateByPrimaryKey(DbUser record);

    DbUser selectByUsername(String username);





}