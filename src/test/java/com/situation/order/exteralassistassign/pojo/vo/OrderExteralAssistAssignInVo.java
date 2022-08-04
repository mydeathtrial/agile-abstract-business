package com.situation.order.exteralassistassign.pojo.vo;

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
 * 描述：外部协办任务-处置-中间表入参
 *
 * @author agile generator
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderExteralAssistAssignInVo extends BaseInParamVo {

    private static final long serialVersionUID = 1L;
    /**
     * 描述：接受的用户id
     */
    @Remark(value = "接受的用户id", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 19, message = "最长为19个字符")
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
    @Length(groups = {Insert.class, Update.class}, max = 19, message = "最长为19个字符")
    private String assigneUserId;
    /**
     * 描述：主键
     */
    @Remark(value = "主键", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 19, message = "最长为19个字符")
    @NotBlank(groups = {Insert.class, Update.class}, message = "主键不能为空字符")
    private String id;
    /**
     * 描述：外部协办处置id
     */
    @Remark(value = "外部协办处置id", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 19, message = "最长为19个字符")
    private String exteralAssistId;
    /**
     * 描述：指派时间
     */
    @Remark(value = "指派时间", ignoreCompare = false)
    private Date assigneTime;
}
