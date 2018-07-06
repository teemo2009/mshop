package com.teemo.shop.service.impl;

import com.teemo.shop.dao.PermDao;
import com.teemo.shop.domain.entity.Permission;
import com.teemo.shop.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService{

    @Resource
    private PermDao permDao;

    @Override
    public void create(Permission permission) {
        Permission hasPerm=permDao.findByPerCode(permission.getPerCode());
        if(ObjectUtils.isEmpty(hasPerm)){
            permDao.save(permission);
        }
    }
}
