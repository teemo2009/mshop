package com.teemo.shop.dao;

import com.teemo.shop.domain.entity.Manager;

public interface ManagerDao extends BaseDao<Manager,Long> {

    Manager findByUsername(String username);

}
