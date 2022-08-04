package com.situation.order.exteralassistassign.pojo.vo;

import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.common.annotation.Remark;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 描述：外部协办任务-处置-中间表出参
 *
 * @author agile generator
 */
@EqualsAndHashCode
@Data
@Builder
public class OrderExteralAssistAssignOutVo implements IBaseOutParamVo {

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
}
