package com.hh.order.service;

import com.hh.order.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @author hh
 * @version 1.0
 * @time 09/01/2024 11:34
 */
@Service
public class OrderService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserFeignClient userFeignClient;

    /**
     * 创建订单
     *
     * @param userId
     * @param commodityCode
     * @param count
     */
    public void create(String userId, String commodityCode, Integer count) {
        //默认价格为100，注意这里的数据类型要和调用的feign入参一致
        Integer orderMoney = count * 100;

        jdbcTemplate.update("insert into order_tbl(user_id, commodity_code, count, money) values(?,?,?,?) ",
                new Object[]{userId, commodityCode, count, orderMoney});

        userFeignClient.reduce(userId, orderMoney);
    }
}
