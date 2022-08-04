package com.situation.order.exteralassistfeedback.pojo.vo;

import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.common.annotation.Remark;
import cloud.agileframework.validate.group.Insert;
import cloud.agileframework.validate.group.Update;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 描述：预警反馈表入参
 *
 * @author agile generator
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderExteralAssistFeedbackInVo extends BaseInParamVo {

    private static final long serialVersionUID = 1L;
    /**
     * 描述：外部协办id
     */
    @Remark(value = "外部协办id", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 19, message = "最长为19个字符")
    private String exteralAssistId;
    /**
     * 描述：附件列表
     */
    @Remark(value = "附件列表", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 255, message = "最长为255个字符")
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
    @Length(groups = {Insert.class, Update.class}, max = 19, message = "最长为19个字符")
    @NotBlank(groups = {Insert.class, Update.class}, message = "主键不能为空字符")
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
    @Length(groups = {Insert.class, Update.class}, max = 255, message = "最长为255个字符")
    private String exteralAssistFeedback;
    /**
     * 描述：处理用户id
     */
    @Remark(value = "处理用户id", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 19, message = "最长为19个字符")
    private String exteralAssistDisposeUser;
}
