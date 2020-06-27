package com.sph.fegin;

import com.sph.fegin.svc.NewsSvc;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("content-microservice")
public interface NewsFegin extends NewsSvc {
}
