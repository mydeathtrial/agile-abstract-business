package com.situation.order.exteralassistassign.pojo.db;

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
 * 描述：外部协办任务-处置-中间表实体
 *
 * @author agile generator
 */
@ToString
@Entity
@SuperBuilder
@Table(name = "order_exteral_assist_assign", catalog = "situation")
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class OrderExteralAssistAssignDo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 描述：接受的用户id
     */
    @Remark(value = "接受的用户id", ignoreCompare = false)
    private String acceptUserId;
    /**
     * 描述：删除标识
     */
    @Remark(value = "删除标识", ignoreCompare = false)
    private Boolean delFlag;
    /**
     * 描述：指派的用户id
     */
    @Remark(value = "指派的用户id", ignoreCompare = false)
    private String assigneUserId;
    /**
     * 描述：主键
     */
    @Remark(value = "主键", ignoreCompare = false)
    private String id;
    /**
     * 描述：外部协办处置id
     */
    @Remark(value = "外部协办处置id", ignoreCompare = false)
    private String exteralAssistId;
    /**
     * 描述：指派时间
     */
    @Remark(value = "指派时间", ignoreCompare = false)
    private Date assigneTime;

    @Basic
    @Column(name = "accept_user_id", length = 19)
    public String getAcceptUserId() {
        return acceptUserId;
    }

    @Column(name = "assigne_user_id", length = 19)
    @Basic
    public String getAssigneUserId() {
        return assigneUserId;
    }

    @Column(name = "id", length = 19, nullable = false)
    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "cloud.agileframework.jpa.dao.IDGeneratorToString")
    public String getId() {
        return id;
    }

    @Column(name = "exteral_assist_id", length = 19)
    @Basic
    public String getExteralAssistId() {
        return exteralAssistId;
    }

    @Column(name = "assigne_time")
    @Basic
    public Date getAssigneTime() {
        return assigneTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderExteralAssistAssignDo that = (OrderExteralAssistAssignDo) o;
        return Objects.equals(acceptUserId, that.acceptUserId)
                && Objects.equals(delFlag, that.delFlag)
                && Objects.equals(assigneUserId, that.assigneUserId)
                && Objects.equals(id, that.id)
                && Objects.equals(exteralAssistId, that.exteralAssistId)
                && Objects.equals(assigneTime, that.assigneTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), acceptUserId, delFlag, assigneUserId, id, exteralAssistId, assigneTime);
    }
}
