package com.hh.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author hh
 * @version 1.0
 * @time 09/01/2024 11:27
 */
@FeignClient(name = "account-service", url = "127.0.0.1:8083")//指定url就是单节点
public interface UserFeignClient {
    @GetMapping("/reduce")//和用户服务(account)的mapping一致
    Boolean reduce(@RequestParam("userId") String userId, @RequestParam("money") Integer money);
}
