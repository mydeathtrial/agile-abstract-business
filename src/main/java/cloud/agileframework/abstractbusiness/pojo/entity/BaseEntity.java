package cloud.agileframework.abstractbusiness.pojo.entity;

import lombok.Builder;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 佟盟
 * 日期 2021-01-28 17:58
 * 描述 数据库实体基类
 * @version 1.0
 * @since 1.0
 */
@MappedSuperclass
@Setter
@SuperBuilder
public class BaseEntity implements IBaseEntity, Serializable {
    private Long createUser;
    private Date createTime;
    private Long updateUser;
    private Date updateTime;
    @Builder.Default
    private Boolean delete = false;

    public BaseEntity() {
        delete = false;
    }

    @Override
    @Basic
    @Column(name = "create_user", updatable = false, length = 20)
    public Long getCreateUser() {
        return createUser;
    }

    @Override
    @Temporal(value = TemporalType.TIMESTAMP)
    @Basic
    @CreationTimestamp
    @Column(name = "create_time", updatable = false)
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    @Basic
    @Column(name = "update_user", insertable = false, length = 20)
    public Long getUpdateUser() {
        return updateUser;
    }

    @Override
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "update_time", insertable = false)
    @UpdateTimestamp
    @Basic
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    @Basic
    @Column(name = "del_flag", length = 1, updatable = false)
    public Boolean getDelete() {
        return delete;
    }
}
