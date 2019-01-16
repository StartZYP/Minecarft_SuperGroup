package com.qq44920040.Minecarft.SuperGroup.Entity;

import java.util.Date;

public class PlayerEntity {
    public PlayerEntity(String PlayerUUid,int HaveContributionPoint,int PostionType){
        this.PlayerUUid = PlayerUUid;
        this.HaveContributionPoint = HaveContributionPoint;
        this.PostionType = PostionType;
    }
    public PlayerEntity(int HaveContributionPoint,int PostionType,Date EnterTime){
        this.EnterTime = EnterTime;
        this.HaveContributionPoint = HaveContributionPoint;
        this.PostionType = PostionType;
    }
    private String PlayerUUid;
    private int HaveContributionPoint;
    private int PostionType;
    private Date EnterTime;

    public Date getEnterTime() {
        return EnterTime;
    }
    public String getPlayerUUid() {
        return PlayerUUid;
    }

    public int getHaveContributionPoint() {
        return HaveContributionPoint;
    }

    public int getPostionType() {
        return PostionType;
    }
}
