package com.teemo.shop.service.impl;

import com.teemo.shop.dao.ManagerDao;
import com.teemo.shop.dao.RoleDao;
import com.teemo.shop.domain.entity.Manager;
import com.teemo.shop.domain.entity.Role;
import com.teemo.shop.service.ManagerService;
import com.teemo.shop.util.Constants;
import com.teemo.shop.util.MUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Service("managerService")
@Transactional
public class ManagerServiceImpl implements ManagerService {

    @Resource
    ManagerDao managerDao;

    @Resource
    RoleDao roleDao;

    @Resource
    MUtil mUtil;

    @Override
    public void initSuperAdmin() {
        Manager superAdmin=managerDao.findByUsername(Constants.SUPER_ADMIN_USERNAME);
        if(ObjectUtils.isEmpty(superAdmin)){
            Manager newSuperAdmin=new Manager();
            Role superAdminRole=  roleDao.findByAlias(Constants.SUPER_ADMIN);
            Set<Role> roles=new HashSet<>();
            roles.add(superAdminRole);
            newSuperAdmin.setUsername(Constants.SUPER_ADMIN_USERNAME);
            newSuperAdmin.setName("超级管理员");
            newSuperAdmin.setPassword(mUtil.MD5(Constants.SUPER_ADMIN_PASSWORD));
            newSuperAdmin.setRoles(roles);
            managerDao.save(newSuperAdmin);
        }
    }

}
