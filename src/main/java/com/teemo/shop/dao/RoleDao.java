package com.teemo.shop.dao;

import com.teemo.shop.domain.entity.Role;

public interface RoleDao extends BaseDao<Role,Long> {
        Role findByAlias(String alias);
}
