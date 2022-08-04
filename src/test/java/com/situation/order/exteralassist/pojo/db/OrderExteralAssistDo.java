package com.situation.order.exteralassist.pojo.db;

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
 * 描述：外部协办任务实体
 *
 * @author agile generator
 */
@ToString
@Entity
@SuperBuilder
@NoArgsConstructor
@Setter
@Table(name = "order_exteral_assist", catalog = "situation")
@AllArgsConstructor
public class OrderExteralAssistDo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 描述：申请单位名称
     */
    @Remark(value = "申请单位名称", ignoreCompare = false)
    private String applyUnitName;
    /**
     * 描述：协办名称（监管接口）
     */
    @Remark(value = "协办名称（监管接口）", ignoreCompare = false)
    private String assistName;
    /**
     * 描述：事件简要描述（监管接口）
     */
    @Remark(value = "事件简要描述（监管接口）", ignoreCompare = false)
    private String eventDesc;
    /**
     * 描述：资源id
     */
    @Remark(value = "资源id", ignoreCompare = false)
    private String resourceIds;
    /**
     * 描述：主键
     */
    @Remark(value = "主键", ignoreCompare = false)
    private String id;
    /**
     * 描述：协办描述（监管接口）
     */
    @Remark(value = "协办描述（监管接口）", ignoreCompare = false)
    private String assistDesc;
    /**
     * 描述：协办单位名称（监管接口）
     */
    @Remark(value = "协办单位名称（监管接口）", ignoreCompare = false)
    private String assistUnitName;
    /**
     * 描述：发送时间（监管接口）
     */
    @Remark(value = "发送时间（监管接口）", ignoreCompare = false)
    private Date sendTime;
    /**
     * 描述：关联的事件及任务描述信息list json（监管接口）
     */
    @Remark(value = "关联的事件及任务描述信息list json（监管接口）", ignoreCompare = false)
    private String relatedEventTasksDesc;
    /**
     * 描述：审核状态未处置0（数据从接口获取的状态）
     */
    @Remark(value = "审核状态未处置0（数据从接口获取的状态）", ignoreCompare = false)
    private Integer exteralAssistProcessStatus;
    /**
     * 描述：删除标识
     */
    @Remark(value = "删除标识", ignoreCompare = false)
    private Boolean delFlag;

    @Basic
    @Column(name = "apply_unit_name")
    public String getApplyUnitName() {
        return applyUnitName;
    }

    @Column(name = "assist_name")
    @Basic
    public String getAssistName() {
        return assistName;
    }

    @Column(name = "event_desc")
    @Basic
    public String getEventDesc() {
        return eventDesc;
    }

    @Column(name = "resource_ids")
    @Basic
    public String getResourceIds() {
        return resourceIds;
    }

    @Column(name = "id", length = 19, nullable = false)
    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "cloud.agileframework.jpa.dao.IDGeneratorToString")
    public String getId() {
        return id;
    }

    @Basic
    @Column(name = "assist_desc")
    public String getAssistDesc() {
        return assistDesc;
    }

    @Column(name = "assist_unit_name")
    @Basic
    public String getAssistUnitName() {
        return assistUnitName;
    }

    @Column(name = "send_time")
    @Basic
    public Date getSendTime() {
        return sendTime;
    }

    @Basic
    @Column(name = "related_event_tasks_desc", length = 2000)
    public String getRelatedEventTasksDesc() {
        return relatedEventTasksDesc;
    }

    @Column(name = "exteral_assist_process_status", length = 10)
    @Basic
    public Integer getExteralAssistProcessStatus() {
        return exteralAssistProcessStatus;
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
        OrderExteralAssistDo that = (OrderExteralAssistDo) o;
        return Objects.equals(applyUnitName, that.applyUnitName)
                && Objects.equals(assistName, that.assistName)
                && Objects.equals(eventDesc, that.eventDesc)
                && Objects.equals(resourceIds, that.resourceIds)
                && Objects.equals(id, that.id)
                && Objects.equals(assistDesc, that.assistDesc)
                && Objects.equals(assistUnitName, that.assistUnitName)
                && Objects.equals(sendTime, that.sendTime)
                && Objects.equals(relatedEventTasksDesc, that.relatedEventTasksDesc)
                && Objects.equals(exteralAssistProcessStatus, that.exteralAssistProcessStatus)
                && Objects.equals(delFlag, that.delFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), applyUnitName, assistName, eventDesc, resourceIds, id, assistDesc, assistUnitName, sendTime, relatedEventTasksDesc, exteralAssistProcessStatus, delFlag);
    }
}
