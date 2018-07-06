package com.teemo.shop.domain.entity;


import com.teemo.shop.domain.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Table
@Entity
@Data
@Accessors(chain = true)
public class Role extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false,unique = true)
    private String alias;
    @ManyToMany
    @JoinTable(name = "role_permission",
            joinColumns =@JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "perm_id"))
    @OrderBy("id ASC")
    private Set<Permission> permissions=new HashSet<>();
}
