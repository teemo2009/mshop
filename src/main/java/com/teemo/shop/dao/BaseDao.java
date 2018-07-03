package com.teemo.shop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseDao<T,B extends Serializable> extends JpaRepository<T,B>,
        JpaSpecificationExecutor<T>,
        QuerydslPredicateExecutor<T> {
}
