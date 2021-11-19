package cloud.agileframework.abstractbusiness.pojo.entity;

import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    public BaseEntity() {
    }

    private Long id;
    private Long createUserId;
    private Date createTime;
    private Long updateUserId;
    private Date updateTime;
    private Boolean delete = false;

    @Override
    @Basic
    @Column(name = "create_user", length = 20)
    public Long getCreateUserId() {
        return createUserId;
    }

    @Override
    @Temporal(value = TemporalType.TIMESTAMP)
    @Basic
    @CreationTimestamp
    @Column(name = "create_time", updatable = false, insertable = false)
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    @Basic
    @Column(name = "update_user", length = 20)
    public Long getUpdateUserId() {
        return updateUserId;
    }

    @Override
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "update_time", updatable = false, insertable = false)
    @UpdateTimestamp
    @Basic
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    @Basic
    @Column(name = "delete", length = 1, updatable = false, insertable = false)
    public Boolean getDelete() {
        return delete;
    }
}
