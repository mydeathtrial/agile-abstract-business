package com.situation.order.exteralassistfeedbackcheck.pojo.db;

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
 * 描述：审批预警反馈表实体
 *
 * @author agile generator
 */
@ToString
@Entity
@SuperBuilder
@NoArgsConstructor
@Setter
@Table(name = "order_exteral_assist_feedback_check", catalog = "situation")
@AllArgsConstructor
public class OrderExteralAssistFeedbackCheckDo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 描述：外部协办id
     */
    @Remark(value = "外部协办id", ignoreCompare = false)
    private String exteralAssistId;
    /**
     * 描述：审核用户id
     */
    @Remark(value = "审核用户id", ignoreCompare = false)
    private String checkUserid;
    /**
     * 描述：结果状态 同意 1 拒绝 0
     */
    @Remark(value = "结果状态 同意 1 拒绝 0", ignoreCompare = false)
    private Integer exteralAssistProcessStatus;
    /**
     * 描述：审核时间
     */
    @Remark(value = "审核时间", ignoreCompare = false)
    private Date checkTime;
    /**
     * 描述：主键
     */
    @Remark(value = "主键", ignoreCompare = false)
    private String id;
    /**
     * 描述：同意/拒绝理由
     */
    @Remark(value = "同意/拒绝理由", ignoreCompare = false)
    private String checkContent;
    /**
     * 描述：外部协办反馈id
     */
    @Remark(value = "外部协办反馈id", ignoreCompare = false)
    private String exteralAssistFeedbackId;
    /**
     * 描述：删除标识
     */
    @Remark(value = "删除标识", ignoreCompare = false)
    private Boolean delFlag;

    @Basic
    @Column(name = "exteral_assist_id", length = 20)
    public String getExteralAssistId() {
        return exteralAssistId;
    }

    @Basic
    @Column(name = "check_userid", length = 19)
    public String getCheckUserid() {
        return checkUserid;
    }

    @Column(name = "exteral_assist_process_status", length = 10)
    @Basic
    public Integer getExteralAssistProcessStatus() {
        return exteralAssistProcessStatus;
    }

    @Basic
    @Column(name = "check_time")
    public Date getCheckTime() {
        return checkTime;
    }

    @Column(name = "id", length = 19, nullable = false)
    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "cloud.agileframework.jpa.dao.IDGeneratorToString")
    public String getId() {
        return id;
    }

    @Basic
    @Column(name = "check_content")
    public String getCheckContent() {
        return checkContent;
    }

    @Basic
    @Column(name = "exteral_assist_feedback_id", length = 19)
    public String getExteralAssistFeedbackId() {
        return exteralAssistFeedbackId;
    }

    @Column(name = "del_flag", length = 1)
    @Basic
    public Boolean getDelFlag() {
        return delFlag;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderExteralAssistFeedbackCheckDo that = (OrderExteralAssistFeedbackCheckDo) o;
        return Objects.equals(exteralAssistId, that.exteralAssistId)
                && Objects.equals(checkUserid, that.checkUserid)
                && Objects.equals(exteralAssistProcessStatus, that.exteralAssistProcessStatus)
                && Objects.equals(checkTime, that.checkTime)
                && Objects.equals(id, that.id)
                && Objects.equals(checkContent, that.checkContent)
                && Objects.equals(exteralAssistFeedbackId, that.exteralAssistFeedbackId)
                && Objects.equals(delFlag, that.delFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), exteralAssistId, checkUserid, exteralAssistProcessStatus, checkTime, id, checkContent, exteralAssistFeedbackId, delFlag);
    }
}
