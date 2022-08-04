package com.situation.order.exteralassistfeedback.pojo.vo;

import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.common.annotation.Remark;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 描述：预警反馈表出参
 *
 * @author agile generator
 */
@EqualsAndHashCode
@Data
@Builder
public class OrderExteralAssistFeedbackOutVo implements IBaseOutParamVo {

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
}
