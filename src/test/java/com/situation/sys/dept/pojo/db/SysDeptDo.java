package com.situation.sys.dept.pojo.db;

import cloud.agileframework.abstractbusiness.pojo.entity.BaseEntity;
import cloud.agileframework.common.annotation.Remark;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * 描述：组织机构表实体
 *
 * @author agile generator
 */
@ToString
@Table(name = "sys_dept", catalog = "situation")
@Entity
@SuperBuilder
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class SysDeptDo extends BaseEntity implements Serializable {

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

    @Column(name = "order_num")
    @Basic
    public String getOrderNum() {
        return orderNum;
    }

    @Basic
    @Column(name = "organization")
    public String getOrganization() {
        return organization;
    }

    @Column(name = "manager", length = 64)
    @Basic
    public String getManager() {
        return manager;
    }

    @Basic
    @Column(name = "inside")
    public String getInside() {
        return inside;
    }

    @Column(name = "path")
    @Basic
    public String getPath() {
        return path;
    }

    @Basic
    @Column(name = "sys_role_id", length = 19)
    public String getSysRoleId() {
        return sysRoleId;
    }

    @Basic
    @Column(name = "parent_id", length = 19)
    public String getParentId() {
        return parentId;
    }

    @Basic
    @Column(name = "secrecy")
    public String getSecrecy() {
        return secrecy;
    }

    @Column(name = "dept_name", length = 64)
    @Basic
    public String getDeptName() {
        return deptName;
    }

    @Column(name = "dept_code", length = 64)
    @Basic
    public String getDeptCode() {
        return deptCode;
    }

    @Basic
    @Column(name = "area", length = 40)
    public String getArea() {
        return area;
    }

    @Column(name = "name_path")
    @Basic
    public String getNamePath() {
        return namePath;
    }

    @Column(name = "del_flag", length = 1)
    @Basic
    public Boolean getDelFlag() {
        return delFlag;
    }

    @Basic
    @Column(name = "sys_dept_id", length = 19, nullable = false)
    public String getSysDeptId() {
        return sysDeptId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SysDeptDo that = (SysDeptDo) o;
        return Objects.equals(orderNum, that.orderNum)
                && Objects.equals(organization, that.organization)
                && Objects.equals(manager, that.manager)
                && Objects.equals(inside, that.inside)
                && Objects.equals(path, that.path)
                && Objects.equals(sysRoleId, that.sysRoleId)
                && Objects.equals(parentId, that.parentId)
                && Objects.equals(secrecy, that.secrecy)
                && Objects.equals(deptName, that.deptName)
                && Objects.equals(deptCode, that.deptCode)
                && Objects.equals(area, that.area)
                && Objects.equals(namePath, that.namePath)
                && Objects.equals(delFlag, that.delFlag)
                && Objects.equals(sysDeptId, that.sysDeptId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), orderNum, organization, manager, inside, path, sysRoleId, parentId, secrecy, deptName, deptCode, area, namePath, delFlag, sysDeptId);
    }
}
