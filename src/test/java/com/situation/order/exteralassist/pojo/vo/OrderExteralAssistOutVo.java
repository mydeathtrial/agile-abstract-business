package com.situation.order.exteralassist.pojo.vo;

import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.common.annotation.Remark;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 描述：外部协办任务出参
 *
 * @author agile generator
 */
@EqualsAndHashCode
@Data
@Builder
public class OrderExteralAssistOutVo implements IBaseOutParamVo {

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
}
