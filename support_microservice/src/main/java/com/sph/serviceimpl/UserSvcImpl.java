package com.sph.serviceimpl;

import com.sph.bean.WebResponse;
import com.sph.entity.User;
import com.sph.service.UserService;
import com.sph.svc.UserSvc;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserSvcImpl implements UserSvc {

    @Autowired
    private UserService userService;

    @Override
    public WebResponse<User> queryUser(String userName) {
        if(StringUtils.isBlank(userName)){
            return WebResponse.fai("99998","userName is null");
        }
        User user = userService.selectByUserName(userName);
        return WebResponse.suc(user);
    }

    @Override
    public WebResponse addUser(@RequestBody User user) {
        if(StringUtils.isBlank(user.getUsername())){
            return WebResponse.fai("99998","userName is null");
        }
        if(StringUtils.isBlank(user.getPassword())){
            return WebResponse.fai("99998","password is null");
        }
        //判断账户是否已经存在
        User userResult = userService.selectByUserName(user.getUsername());
        if(userResult != null){
            return WebResponse.fai("10005","account already exists");
        }
        userService.addUser(user);
        return WebResponse.create().success();
    }
}
