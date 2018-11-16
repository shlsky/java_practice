package com.entity;

import java.util.Date;

public class RewardItemGrantRecord {
    private Long id;

    private Long userId;

    private Long rewardId;

    private String rewardNum;

    private Date dispenseTime;

    private Byte receiveStatus;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRewardId() {
        return rewardId;
    }

    public void setRewardId(Long rewardId) {
        this.rewardId = rewardId;
    }

    public String getRewardNum() {
        return rewardNum;
    }

    public void setRewardNum(String rewardNum) {
        this.rewardNum = rewardNum;
    }

    public Date getDispenseTime() {
        return dispenseTime;
    }

    public void setDispenseTime(Date dispenseTime) {
        this.dispenseTime = dispenseTime;
    }

    public Byte getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(Byte receiveStatus) {
        this.receiveStatus = receiveStatus;
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