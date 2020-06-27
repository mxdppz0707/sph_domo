package com.sph.dao;

import com.sph.entity.User;
import com.sph.entity.UserExample;
import com.sph.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Repository
public class UserDao {

    @Resource
    private UserMapper userMapper;

    public User selectByUserName(String userName){
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(userName);
        List<User> list = userMapper.selectByExample(example);
        return list.size() == 0 ? null : list.get(0);
    }

    public void addUser(User user){
        user.setCreateTime(new Date());
        userMapper.insert(user);
    }
}
