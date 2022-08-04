package com.situation.sys.dept.pojo.vo;

import cloud.agileframework.abstractbusiness.pojo.vo.BaseInParamVo;
import cloud.agileframework.common.annotation.Remark;
import cloud.agileframework.validate.group.Insert;
import cloud.agileframework.validate.group.Update;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 描述：组织机构表入参
 *
 * @author agile generator
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDeptInVo extends BaseInParamVo {

    private static final long serialVersionUID = 1L;
    /**
     * 描述：排序
     */
    @Remark(value = "排序", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 255, message = "最长为255个字符")
    private String orderNum;
    /**
     * 描述：是否是组织
     */
    @Remark(value = "是否是组织", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 255, message = "最长为255个字符")
    private String organization;
    /**
     * 描述：部门负责人
     */
    @Remark(value = "部门负责人", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 64, message = "最长为64个字符")
    private String manager;
    /**
     * 描述：是否内置
     */
    @Remark(value = "是否内置", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 255, message = "最长为255个字符")
    private String inside;
    /**
     * 描述：路径
     */
    @Remark(value = "路径", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 255, message = "最长为255个字符")
    private String path;
    /**
     * 描述：角色主键
     */
    @Remark(value = "角色主键", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 19, message = "最长为19个字符")
    private String sysRoleId;
    /**
     * 描述：上级
     */
    @Remark(value = "上级", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 19, message = "最长为19个字符")
    private String parentId;
    /**
     * 描述：密级
     */
    @Remark(value = "密级", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 255, message = "最长为255个字符")
    private String secrecy;
    /**
     * 描述：名称
     */
    @Remark(value = "名称", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 64, message = "最长为64个字符")
    private String deptName;
    /**
     * 描述：组织机构代码
     */
    @Remark(value = "组织机构代码", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 64, message = "最长为64个字符")
    private String deptCode;
    /**
     * 描述：地区
     */
    @Remark(value = "地区", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 40, message = "最长为40个字符")
    private String area;
    /**
     * 描述：中文路径
     */
    @Remark(value = "中文路径", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 255, message = "最长为255个字符")
    private String namePath;
    @Remark(value = "", ignoreCompare = false)
    private Boolean delFlag;
    /**
     * 描述：主键
     */
    @Remark(value = "主键", ignoreCompare = false)
    @Length(groups = {Insert.class, Update.class}, max = 19, message = "最长为19个字符")
    @NotBlank(groups = {Insert.class, Update.class}, message = "主键不能为空字符")
    private String sysDeptId;
}
