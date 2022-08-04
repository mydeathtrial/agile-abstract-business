package com.situation.order.exteralassistfeedbackcheck.pojo.vo;

import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.common.annotation.Remark;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 描述：审批预警反馈表出参
 *
 * @author agile generator
 */
@EqualsAndHashCode
@Data
@Builder
public class OrderExteralAssistFeedbackCheckOutVo implements IBaseOutParamVo {

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
}
