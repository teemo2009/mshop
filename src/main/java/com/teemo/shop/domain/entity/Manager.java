package com.teemo.shop.domain.entity;

import com.teemo.shop.domain.bean.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Table
@Entity
@Data
@Accessors(chain = true)
public class Manager extends BaseEntity {
    @Column(unique = true)
    @NotNull(message = "用户名不能为空")
    private String username;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @NotNull(message = "密码不能为空")
    private String password;

    @ManyToMany
    @JoinTable(name = "manager_role",
            joinColumns =@JoinColumn(name = "manager_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @OrderBy("id ASC")
    private Set<Role> roles=new HashSet<>();

}
