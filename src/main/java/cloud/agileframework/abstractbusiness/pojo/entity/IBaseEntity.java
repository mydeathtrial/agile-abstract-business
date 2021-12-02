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
     * 取创建人主键
     *
     * @return 创建人主键
     */
    Long getCreateUser();

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
    Long getUpdateUser();

    /**
     * 取更新时间
     *
     * @return 更新时间
     */
    Date getUpdateTime();

    /**
     * 取是否删除
     *
     * @return 是否删除
     */
    Boolean getDelete();

    /**
     * 设置创建人主键
     *
     * @param createUserId 创建人主键
     */
    void setCreateUser(Long createUserId);

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
    void setUpdateUser(Long updateUserId);

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    void setUpdateTime(Date updateTime);

    /**
     * 是否已删除
     *
     * @param delFlag 是否已删除
     */
    void setDelete(Boolean delFlag);
}
