package com.sph.fegin.svc;

import com.sph.bean.WebResponse;
import com.sph.fegin.bean.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserSvc {

    @RequestMapping(value = "/support/queryUser", method = RequestMethod.POST)
    public WebResponse<User> queryUser(@RequestParam("userName") String userName);

    @RequestMapping(value = "/support/UserRegister", method = RequestMethod.POST)
    public WebResponse addUser(@RequestBody User user);
}
