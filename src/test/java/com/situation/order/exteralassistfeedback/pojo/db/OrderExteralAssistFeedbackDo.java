package com.situation.order.exteralassistfeedback.pojo.db;

import cloud.agileframework.abstractbusiness.pojo.entity.BaseEntity;
import cloud.agileframework.common.annotation.Remark;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 描述：预警反馈表实体
 *
 * @author agile generator
 */
@ToString
@Entity
@SuperBuilder
@NoArgsConstructor
@Setter
@AllArgsConstructor
@Table(name = "order_exteral_assist_feedback", catalog = "situation")
public class OrderExteralAssistFeedbackDo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 描述：外部协办id
     */
    @Remark(value = "外部协办id", ignoreCompare = false)
    private String exteralAssistId;
    /**
     * 描述：附件列表
     */
    @Remark(value = "附件列表", ignoreCompare = false)
    private String resourceIds;
    /**
     * 描述：处置时间
     */
    @Remark(value = "处置时间", ignoreCompare = false)
    private Date exteralAssistDisposeTime;
    /**
     * 描述：主键
     */
    @Remark(value = "主键", ignoreCompare = false)
    private String id;
    /**
     * 描述：删除标识
     */
    @Remark(value = "删除标识", ignoreCompare = false)
    private Boolean delFlag;
    /**
     * 描述：反馈结果
     */
    @Remark(value = "反馈结果", ignoreCompare = false)
    private String exteralAssistFeedback;
    /**
     * 描述：处理用户id
     */
    @Remark(value = "处理用户id", ignoreCompare = false)
    private String exteralAssistDisposeUser;

    @Column(name = "exteral_assist_id", length = 19)
    @Basic
    public String getExteralAssistId() {
        return exteralAssistId;
    }

    @Column(name = "resource_ids")
    @Basic
    public String getResourceIds() {
        return resourceIds;
    }

    @Column(name = "exteral_assist_dispose_time")
    @Basic
    public Date getExteralAssistDisposeTime() {
        return exteralAssistDisposeTime;
    }

    @Column(name = "id", length = 19, nullable = false)
    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "cloud.agileframework.jpa.dao.IDGeneratorToString")
    public String getId() {
        return id;
    }

    @Column(name = "del_flag", length = 1)
    @Basic
    public Boolean getDelFlag() {
        return delFlag;
    }

    @Column(name = "exteral_assist_feedback")
    @Basic
    public String getExteralAssistFeedback() {
        return exteralAssistFeedback;
    }

    @Column(name = "exteral_assist_dispose_user", length = 19)
    @Basic
    public String getExteralAssistDisposeUser() {
        return exteralAssistDisposeUser;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderExteralAssistFeedbackDo that = (OrderExteralAssistFeedbackDo) o;
        return Objects.equals(exteralAssistId, that.exteralAssistId)
                && Objects.equals(resourceIds, that.resourceIds)
                && Objects.equals(exteralAssistDisposeTime, that.exteralAssistDisposeTime)
                && Objects.equals(id, that.id)
                && Objects.equals(delFlag, that.delFlag)
                && Objects.equals(exteralAssistFeedback, that.exteralAssistFeedback)
                && Objects.equals(exteralAssistDisposeUser, that.exteralAssistDisposeUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), exteralAssistId, resourceIds, exteralAssistDisposeTime, id, delFlag, exteralAssistFeedback, exteralAssistDisposeUser);
    }
}
