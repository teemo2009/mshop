package com.teemo.shop.domain.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.teemo.shop.domain.bean.AbstractEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = "id", callSuper = false)
@MappedSuperclass
@TypeDefs(
        @TypeDef(name = "json", typeClass = JsonStringType.class)
)
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public <T> T setId(Long id) {
        this.id = id;
        return (T) this;
    }
}
