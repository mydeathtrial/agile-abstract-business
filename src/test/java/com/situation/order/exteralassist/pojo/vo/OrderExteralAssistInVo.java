package com.situation.order.exteralassist.pojo.vo;

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
 * 描述：外部协办任务入参
 *
 * @author agile generator
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderExteralAssistInVo extends BaseInParamVo {

    private static final long serialVersionUID = 1L;
    /**
     * 描述：申请单位名称
     */
    @Remark(value = "申请单位名称", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 255, message = "最长为255个字符")
    private String applyUnitName;
    /**
     * 描述：协办名称（监管接口）
     */
    @Remark(value = "协办名称（监管接口）", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 255, message = "最长为255个字符")
    private String assistName;
    /**
     * 描述：事件简要描述（监管接口）
     */
    @Remark(value = "事件简要描述（监管接口）", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 255, message = "最长为255个字符")
    private String eventDesc;
    /**
     * 描述：资源id
     */
    @Remark(value = "资源id", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 255, message = "最长为255个字符")
    private String resourceIds;
    /**
     * 描述：主键
     */
    @Remark(value = "主键", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 19, message = "最长为19个字符")
    @NotBlank(groups = {Insert.class, Update.class}, message = "主键不能为空字符")
    private String id;
    /**
     * 描述：协办描述（监管接口）
     */
    @Remark(value = "协办描述（监管接口）", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 255, message = "最长为255个字符")
    private String assistDesc;
    /**
     * 描述：协办单位名称（监管接口）
     */
    @Remark(value = "协办单位名称（监管接口）", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 255, message = "最长为255个字符")
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
    @Length(groups = {Insert.class, Update.class}, max = 2000, message = "最长为2000个字符")
    private String relatedEventTasksDesc;
    /**
     * 描述：审核状态未处置0（数据从接口获取的状态）
     */
    @Remark(value = "审核状态未处置0（数据从接口获取的状态）", ignoreCompare = false)
    @Min(groups = {Insert.class, Update.class}, value = 0)
    @Max(groups = {Insert.class, Update.class}, value = 2147483647)
    private Integer exteralAssistProcessStatus;
    /**
     * 描述：删除标识
     */
    @Remark(value = "删除标识", ignoreCompare = false)
    private Boolean delFlag;
}
