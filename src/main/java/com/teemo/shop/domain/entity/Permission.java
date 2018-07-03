package com.teemo.shop.domain.entity;

import com.teemo.shop.domain.bean.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Table
@Entity
@Data
@Accessors(chain = true)
public class Permission  extends AbstractEntity{
    @Id
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String perCode;
    @Column
    private Long parent;
}
