package com.example.demo.service.impl;

import com.example.demo.bean.User;
import com.example.demo.dao.UserDao;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void modify(User user) {
        userDao.updateByPrimaryKey(user);
    }

    @Override
    public User getUserByAccount(String account) {

        return userDao.getUserByName(account);
    }

    @Override
    public User getUserById(int id) {
        return userDao.selectByPrimaryKey(id);
    }

    @Override
    public boolean addUser(User user) {
        boolean result = false;
        try {
            userDao.insert(user);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
