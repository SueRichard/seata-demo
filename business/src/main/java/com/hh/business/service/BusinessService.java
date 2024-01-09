package com.hh.business.service;

import com.hh.business.feign.OrderFeignClient;
import com.hh.business.feign.StorageFeignClient;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author hh
 * @version 1.0
 * @time 09/01/2024 14:06
 */
@Service
public class BusinessService {

    @Autowired
    private StorageFeignClient storageFeignClient;

    @Autowired
    private OrderFeignClient orderFeignClient;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 减库存，下订单
     * seata AT模式
     * @param userId
     * @param commodityCode
     * @param orderCount
     */
    @GlobalTransactional
    public void purchase(String userId, String commodityCode, Integer orderCount) {
        //减库存，库存微服务
        storageFeignClient.deduct(commodityCode, orderCount);
        //订单微服务创建订单，再通过openfeign调用用户微服务扣款
        orderFeignClient.create(userId, commodityCode, orderCount);
        if (!validData()) {
            throw new RuntimeException("账户或库存不足，执行回滚");
        }
    }

    /**
     * 校验余额是否足够，库存是否足够
     *
     * @return
     */
    private boolean validData() {
        Map accountMap = jdbcTemplate.queryForMap("select * from account_tbl where user_id = 'U100000'");
        if (Integer.parseInt(accountMap.get("money").toString()) < 0) {
            return false;
        }
        Map storageMap = jdbcTemplate.queryForMap("select * from storage_tbl where commodity_code = 'C100000'");
        if (Integer.parseInt(storageMap.get("count").toString()) < 0) {
            return false;
        }
        return true;
    }

    /**
     * 每次初始化用户余额和商品库存
     */
    @PostConstruct
    public void initData() {
        jdbcTemplate.update("delete from account_tbl");
        jdbcTemplate.update("delete from storage_tbl");
        jdbcTemplate.update("delete from order_tbl");
        jdbcTemplate.update("insert into account_tbl(user_id, money) values('U100000','10000')");
        jdbcTemplate.update("insert into storage_tbl(commodity_code, count) values('C100000','200')");
    }
}
