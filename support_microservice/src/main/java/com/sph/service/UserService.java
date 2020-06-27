package com.sph.service;

import com.sph.dao.UserDao;
import com.sph.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User selectByUserName(String userName){
        return userDao.selectByUserName(userName);
    }

    public void addUser(User user){
        userDao.addUser(user);
    }
}
