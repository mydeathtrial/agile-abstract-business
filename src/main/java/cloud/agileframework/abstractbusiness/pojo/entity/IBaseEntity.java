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
    /**
     * 取主键
     *
     * @return 主键
     */
    Long getId();

    /**
     * 取创建人主键
     *
     * @return 创建人主键
     */
    Long getCreateUserId();

    /**
     * 取创建时间
     *
     * @return 创建时间
     */
    Date getCreateTime();

    /**
     * 取更新人主键
     *
     * @return 更新人主键
     */
    Long getUpdateUserId();

    /**
     * 取更新时间
     *
     * @return 更新时间
     */
    Date getUpdateTime();

    /**
     * 取是否启用
     *
     * @return 是否启用
     */
    Integer getIsEnabled();

    /**
     * 取是否删除
     *
     * @return 是否删除
     */
    Integer getDelFlag();

    /**
     * 取乐观锁版本号
     *
     * @return 乐观锁版本号
     */
    Integer getVersion();

    /**
     * 设置主键
     *
     * @param id 主键
     */
    void setId(Long id);

    /**
     * 设置创建人主键
     *
     * @param createUserId 创建人主键
     */
    void setCreateUserId(Long createUserId);

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    void setCreateTime(Date createTime);

    /**
     * 设置更新人主键
     *
     * @param updateUserId 更新人主键
     */
    void setUpdateUserId(Long updateUserId);

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    void setUpdateTime(Date updateTime);

    /**
     * 是否启用
     *
     * @param isEnable 是否启用
     */
    void setIsEnabled(Integer isEnable);

    /**
     * 是否已删除
     *
     * @param delFlag 是否已删除
     */
    void setDelFlag(Integer delFlag);

    /**
     * 设置乐观锁
     *
     * @param version 版本
     */
    void setVersion(Integer version);
}
