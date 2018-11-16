package com.entity;

import java.util.Date;

public class RewardPrizeType {
    private Long id;

    private String prizeName;

    private String prizeType;

    private String iconUrl;

    private Integer prizeInventory;

    private String prizeValue;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public String getPrizeType() {
        return prizeType;
    }

    public void setPrizeType(String prizeType) {
        this.prizeType = prizeType;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getPrizeInventory() {
        return prizeInventory;
    }

    public void setPrizeInventory(Integer prizeInventory) {
        this.prizeInventory = prizeInventory;
    }

    public String getPrizeValue() {
        return prizeValue;
    }

    public void setPrizeValue(String prizeValue) {
        this.prizeValue = prizeValue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}