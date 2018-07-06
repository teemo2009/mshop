package com.teemo.shop.config.init;

import com.google.common.collect.Lists;
import com.teemo.shop.domain.entity.Permission;
import com.teemo.shop.service.ManagerService;
import com.teemo.shop.service.PermissionService;
import com.teemo.shop.service.RoleService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class InitConfig implements ApplicationListener<ApplicationContextEvent> {
    @Resource
    PermissionService permissionService;
    @Resource
    RoleService roleService;
    @Resource
    ManagerService managerService;
    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        //spring启动后执行  
        if (event.getApplicationContext().getParent() == null) {
            /**后台权限初始化*/
            authorManager();
            authorProduct();
            initSuperAdmin();
        }

    }

    /**
     * 后台管理
     * */
    private void authorManager(){
        Permission module = new Permission().setId(100L).setName("后台管理").setPerCode("system:module").setParent(0L);
        Permission managerModule = new Permission().setId(101L).setName("管理员信息").setPerCode("system:manager-module");
        Permission roleModule = new Permission().setId(102L).setName("管理员角色").setPerCode("system:role-module");
        List<Permission> list = Lists.newArrayList(module, managerModule, roleModule);
        for (Permission p : list) {
            permissionService.create(p);
        }
    }

    /**
     *  商品管理
     * */
    private void authorProduct(){
        Permission module = new Permission().setId(200L).setName("商品管理").setPerCode("product:module").setParent(0L);
        Permission productTypeModule = new Permission().setId(201L).setName("商品分类").setPerCode("product:class-module");
        Permission productModule=new Permission().setId(202L).setName("商品信息").setPerCode("product:info-module");
        List<Permission> list = Lists.newArrayList(module, productTypeModule, productModule);
        for (Permission p : list) {
            permissionService.create(p);
        }
    }

    /**
     *  初始化 超级管理员
     * */
    private void initSuperAdmin(){
        roleService.initSuperAdmin();
        managerService.initSuperAdmin();
    }
}
