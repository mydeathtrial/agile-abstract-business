package com.situation.sys.dept.pojo.vo;

import cloud.agileframework.abstractbusiness.pojo.vo.IBaseOutParamVo;
import cloud.agileframework.common.annotation.Remark;
import cloud.agileframework.dictionary.annotation.Dictionary;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述：组织机构表出参
 *
 * @author agile generator
 */
@EqualsAndHashCode
@Data
@Builder
public class SysDeptOutVo implements IBaseOutParamVo {

    private static final long serialVersionUID = 1L;
    /**
     * 描述：排序
     */
    @Remark(value = "排序", ignoreCompare = false)
    private String orderNum;
    /**
     * 描述：是否是组织
     */
    @Remark(value = "是否是组织", ignoreCompare = false)
    private String organization;
    /**
     * 描述：部门负责人
     */
    @Remark(value = "部门负责人", ignoreCompare = false)
    private String manager;
    /**
     * 描述：是否内置
     */
    @Remark(value = "是否内置", ignoreCompare = false)
    private String inside;
    /**
     * 描述：路径
     */
    @Remark(value = "路径", ignoreCompare = false)
    private String path;
    /**
     * 描述：角色主键
     */
    @Remark(value = "角色主键", ignoreCompare = false)
    private String sysRoleId;
    /**
     * 描述：上级
     */
    @Remark(value = "上级", ignoreCompare = false)
    private String parentId;
    /**
     * 描述：密级
     */
    @Remark(value = "密级", ignoreCompare = false)
    private String secrecy;
    /**
     * 描述：名称
     */
    @Remark(value = "名称", ignoreCompare = false)
    private String deptName;
    /**
     * 描述：组织机构代码
     */
    @Remark(value = "组织机构代码", ignoreCompare = false)
    private String deptCode;
    /**
     * 描述：地区
     */
    @Remark(value = "地区", ignoreCompare = false)
    private String area;
    /**
     * 描述：中文路径
     */
    @Remark(value = "中文路径", ignoreCompare = false)
    private String namePath;
    @Remark(value = "", ignoreCompare = false)
    private Boolean delFlag;
    /**
     * 描述：主键
     */
    @Remark(value = "主键", ignoreCompare = false)
    private String sysDeptId;
    /**
     * 描述：地区
     */
    @Remark(value = "地区", ignoreCompare = false)
    @Dictionary(split = "/", fieldName = {"area"}, dicCode = "area")
    private String areaName;
}
