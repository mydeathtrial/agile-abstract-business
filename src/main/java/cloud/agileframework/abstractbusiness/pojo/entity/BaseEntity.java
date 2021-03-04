package cloud.agileframework.abstractbusiness.pojo.entity;

import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
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
public class BaseEntity implements IBaseEntity,Serializable {
    public BaseEntity() {
    }
    private Long id;
    private Long createUserId;
    private Date createTime;
    private Long updateUserId;
    private Date updateTime;
    private Integer isEnabled = 1;
    private Integer delFlag = 0;
    private Integer version;

    @Override
    @Column(name = "`id`", length = 20, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "cloud.agileframework.jpa.dao.IDGenerator")
    public Long getId() {
        return id;
    }

    @Override
    @Basic
    @Column(name = "create_user_id", length = 20)
    public Long getCreateUserId() {
        return createUserId;
    }

    @Override
    @Temporal(value = TemporalType.TIMESTAMP)
    @Basic
    @Column(name = "create_time", updatable = false)
    @CreationTimestamp
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    @Basic
    @Column(name = "update_user_id", length = 20)
    public Long getUpdateUserId() {
        return updateUserId;
    }

    @Override
    @Temporal(value = TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Basic
    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    @Column(name = "is_enabled", length = 11)
    @Basic
    public Integer getIsEnabled() {
        return isEnabled;
    }

    @Override
    @Basic
    @Column(name = "del_flag", length = 11, insertable = false, updatable = false)
    public Integer getDelFlag() {
        return delFlag;
    }

    @Override
    @Basic
    @Column(name = "`version`", length = 11)
    public Integer getVersion() {
        return version;
    }
}
