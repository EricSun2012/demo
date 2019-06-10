package com.example.demo.dao;


import com.example.demo.bean.User;
import com.example.demo.framework.BaseDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseDao<User> {
    User getUserByName(String account);
}
