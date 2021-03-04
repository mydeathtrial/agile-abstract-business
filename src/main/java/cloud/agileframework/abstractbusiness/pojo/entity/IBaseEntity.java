package cloud.agileframework.abstractbusiness.pojo.entity;

import java.util.Date;

/**
 * @author 佟盟
 * 日期 2021-02-23 13:43
 * 描述 TODO
 * @version 1.0
 * @since 1.0
 */
public interface IBaseEntity {
    Long getId();

    Long getCreateUserId();

    Date getCreateTime();

    Long getUpdateUserId();

    Date getUpdateTime();

    Integer getIsEnabled();

    Integer getDelFlag();

    Integer getVersion();

    void setId(Long id);

    void setCreateUserId(Long createUserId);

    void setCreateTime(Date createTime);

    void setUpdateUserId(Long updateUserId);

    void setUpdateTime(Date updateTime);

    void setIsEnabled(Integer isEnable);

    void setDelFlag(Integer delFlag);

    void setVersion(Integer version);
}
