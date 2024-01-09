package com.hh.storage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @author hh
 * @version 1.0
 * @time 09/01/2024 09:45
 */
@Service
public class StorageService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    ;

    /**
     * 扣减库存
     *
     * @param commodityCode
     * @param count
     */
    public void deduct(String commodityCode, int count) {
        jdbcTemplate.update("update storage_tbl set count = count - ? where commodity_code = ?",
                new Object[]{count, commodityCode});
    }

}
