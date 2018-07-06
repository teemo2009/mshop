package com.teemo.shop.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teemo.shop.dao.AccountDao;
import com.teemo.shop.domain.bean.Result;
import com.teemo.shop.domain.dto.AccountDTO;
import com.teemo.shop.domain.entity.Account;
import com.teemo.shop.domain.entity.QAccount;
import com.teemo.shop.exception.BusinessException;
import com.teemo.shop.service.AccountService;
import com.teemo.shop.util.CodeUtil;
import com.teemo.shop.util.JWTUtil;
import com.teemo.shop.util.MUtil;
import com.teemo.shop.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

@Service("accountService")
@Slf4j
@Transactional
public class AccountServiceImpl implements AccountService {
    @Resource
    AccountDao accountDao;
    @Resource
    MUtil mUtil;
    @Value("${redis.manager}")
    private String REDIS_MANAGER_KEY;
    @Resource
    private RedisUtil redisUtil;
    //实体管理者
    @Resource
    private EntityManager entityManager;
    @Resource
    private JWTUtil jwtUtil;
    @Resource
    private CodeUtil codeUtil;

    //JPA查询工厂 实例化放置在每一个方法中  保证线程封闭安全的对象
    private JPAQueryFactory queryFactory;

    @Override
    public Result register(Account account) {
        queryFactory=new JPAQueryFactory(entityManager);
        QAccount qAccount=QAccount.account;
        Account hasAccount=queryFactory.selectFrom(qAccount)
                .where(qAccount.username.eq(account.getUsername())).fetchOne();
        if(hasAccount!=null){
            throw new BusinessException("用户名重复");
        }
        String md5Pwd=mUtil.MD5(account.getPassword());
        account.setPassword(md5Pwd);
        accountDao.save(account);
        return Result.success().message("注册成功");
    }

    @Override
    public Result login(Account account) {
        queryFactory=new JPAQueryFactory(entityManager);
        String username=account.getUsername();
        String md5Pwd=mUtil.MD5(account.getPassword());
        QAccount qAccount=QAccount.account;
        Account hasAccount= queryFactory.selectFrom(qAccount)
                .where(qAccount.username.eq(username).and(qAccount.password.eq(md5Pwd))).fetchOne();
        if(ObjectUtils.isEmpty(hasAccount)){
            throw new BusinessException("用户名或密码错误");
        }
        /**jwt生成token，并用dto对象返回至客户端*/
        AccountDTO accountDTO= new AccountDTO();
        String token= jwtUtil.sign(hasAccount.getUsername());
        String refreshToken=codeUtil.encode(token);
        /**token时效性1小时*/
        accountDTO.setId(hasAccount.getId());
        accountDTO.setToken(token);
        accountDTO.setRefreshToken(refreshToken);
        accountDTO.setUsername(hasAccount.getUsername());
        /**redi存入token*/
        String key=REDIS_MANAGER_KEY+hasAccount.getUsername();
        redisUtil.put(key,accountDTO);
        return Result.success().message("登录成功").data(accountDTO);
    }
}
