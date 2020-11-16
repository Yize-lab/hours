package com.tencent.hours.entity.base;

import com.alibaba.excel.annotation.ExcelIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ghj
 * @Description
 * @date 2020/11/4
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = -7714968381603359948L;
    @ExcelIgnore
    private Long id;
    @ExcelIgnore
    private LocalDateTime createTime;
    @ExcelIgnore
    private LocalDateTime updateTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Column(updatable = false)
    @CreationTimestamp
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新时间可用@PreUpdate代替
     *
     * @return
     */
    @CreationTimestamp
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @PreUpdate
    public void preUpdate() {
        this.updateTime = LocalDateTime.now();
    }

}
