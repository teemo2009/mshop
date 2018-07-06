package com.teemo.shop.dao;

import com.teemo.shop.domain.entity.Permission;

public interface PermDao extends BaseDao<Permission,Long> {
    Permission findByPerCode(String perCode);
}
