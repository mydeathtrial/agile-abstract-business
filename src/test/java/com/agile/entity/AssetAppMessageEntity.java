package com.agile.entity;

import java.io.Serializable;
import cloud.agileframework.generator.annotation.Remark;
import java.util.Date;

/**
 * 描述：资产-应用信息
 * @author agile generator
 */
public class AssetAppMessageEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    private String phone;
    private Long assetId;
    private Long appId;
    private Boolean delFlag;
    private String appIp;
    private String updateUser;
    private String appName;
    private String internet;
    private String admin;
    private String appPort;
    private Long businessId;
    private String appNo;
    private Date updateTime;
    private Long departId;
    private Date createTime;
    private String loadBalanceIp;
    private String createUser;

    public String getPhone() {
        return phone;
    }

    public Long getAssetId() {
        return assetId;
    }

    public Long getAppId() {
        return appId;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public String getAppIp() {
        return appIp;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public String getAppName() {
        return appName;
    }

    public String getInternet() {
        return internet;
    }

    public String getAdmin() {
        return admin;
    }

    public String getAppPort() {
        return appPort;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public String getAppNo() {
        return appNo;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Long getDepartId() {
        return departId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getLoadBalanceIp() {
        return loadBalanceIp;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public void setAppIp(String appIp) {
        this.appIp = appIp;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setInternet(String internet) {
        this.internet = internet;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public void setAppPort(String appPort) {
        this.appPort = appPort;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setDepartId(Long departId) {
        this.departId = departId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setLoadBalanceIp(String loadBalanceIp) {
        this.loadBalanceIp = loadBalanceIp;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Override
    public AssetAppMessageEntity clone() {
        try {
            return (AssetAppMessageEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            return this;
        }
    }
}
