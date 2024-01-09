package com.hh.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @author hh
 * @version 1.0
 * @time 09/01/2024 10:58
 */
@Service
public class AccountService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 扣款
     *
     * @param userId
     * @param money
     */
    public void reduce(String userId, Integer money) {
        jdbcTemplate.update("update account_tbl set money = money - ? where user_id = ?", new Object[]{money, userId});
    }
}
