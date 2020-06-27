package com.sph.fegin;

import com.sph.fegin.svc.UserSvc;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("support-microservice")
public interface UserFegin extends UserSvc{
}
