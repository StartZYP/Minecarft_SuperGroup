package com.qq44920040.Minecarft.SuperGroup.Entity;

import java.util.Date;

public class PlayerEntity {
    public PlayerEntity(String PlayerUUid,int HaveContributionPoint,int PostionType){
        this.PlayerUUid = PlayerUUid;
        this.HaveContributionPoint = HaveContributionPoint;
        this.PostionType = PostionType;
    }
    int GroupId;
    private String PlayerUUid;
    private int HaveContributionPoint;
    private int PostionType;
    Date EnterTime;
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
