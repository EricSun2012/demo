package com.example.demo.service;

import com.example.demo.bean.User;
import org.springframework.stereotype.Repository;

@Repository("UserService")
public interface UserService {

    User getUserByAccount(String account);

    public User getUserById(int id);

    boolean addUser(User user);

    void modify(User user);

}
