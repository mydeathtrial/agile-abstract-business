package com.situation.order.exteralassistfeedbackcheck.pojo.vo;

import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.common.annotation.Remark;
import cloud.agileframework.validate.group.Insert;
import cloud.agileframework.validate.group.Update;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 描述：审批预警反馈表入参
 *
 * @author agile generator
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderExteralAssistFeedbackCheckInVo extends BaseInParamVo {

    private static final long serialVersionUID = 1L;
    /**
     * 描述：外部协办id
     */
    @Remark(value = "外部协办id", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 20, message = "最长为20个字符")
    private String exteralAssistId;
    /**
     * 描述：审核用户id
     */
    @Remark(value = "审核用户id", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 19, message = "最长为19个字符")
    private String checkUserid;
    /**
     * 描述：结果状态 同意 1 拒绝 0
     */
    @Remark(value = "结果状态 同意 1 拒绝 0", ignoreCompare = false)
    @Min(groups = {Insert.class, Update.class}, value = 0)
    @Max(groups = {Insert.class, Update.class}, value = 2147483647)
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
    @Length(groups = {Insert.class, Update.class}, max = 19, message = "最长为19个字符")
    @NotBlank(groups = {Insert.class, Update.class}, message = "主键不能为空字符")
    private String id;
    /**
     * 描述：同意/拒绝理由
     */
    @Remark(value = "同意/拒绝理由", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 255, message = "最长为255个字符")
    private String checkContent;
    /**
     * 描述：外部协办反馈id
     */
    @Remark(value = "外部协办反馈id", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 19, message = "最长为19个字符")
    private String exteralAssistFeedbackId;
    /**
     * 描述：删除标识
     */
    @Remark(value = "删除标识", ignoreCompare = false)
    private Boolean delFlag;
}
