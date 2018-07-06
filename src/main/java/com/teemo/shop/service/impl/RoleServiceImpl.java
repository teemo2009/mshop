package com.teemo.shop.service.impl;

import com.teemo.shop.dao.PermDao;
import com.teemo.shop.dao.RoleDao;
import com.teemo.shop.domain.entity.Permission;
import com.teemo.shop.domain.entity.Role;
import com.teemo.shop.service.RoleService;
import com.teemo.shop.util.Constants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;

@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService{

    @Resource
    RoleDao roleDao;

    @Resource
    PermDao permDao;

    @Override
    public void initSuperAdmin() {
       Role superAdmin= roleDao.findByAlias(Constants.SUPER_ADMIN);
       List<Permission> perms=permDao.findAll();
       if(ObjectUtils.isEmpty(superAdmin)){
           Role newSuperAdmin=new Role();
           newSuperAdmin.setName("超级管理员");
           newSuperAdmin.setAlias(Constants.SUPER_ADMIN);
           newSuperAdmin.setPermissions(new HashSet<>(perms));
           roleDao.save(newSuperAdmin);
       }else{
           superAdmin.getPermissions().clear();
           superAdmin.setPermissions(new HashSet<>(perms));
       }
    }
}
