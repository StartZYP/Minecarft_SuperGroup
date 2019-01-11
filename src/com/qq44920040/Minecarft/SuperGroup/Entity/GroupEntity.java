package com.qq44920040.Minecarft.SuperGroup.Entity;

import com.qq44920040.Minecarft.SuperGroup.GroupConfig;

import java.util.Date;
import java.util.Map;

public class GroupEntity {
    private int GroupID;
    private String GroupName;
    private int GroupContributionPoint;
    private int UsedGroupContributionPoist;
    private int GroupLevel;
    private Date CreateTime;
    private int MaxHuManNumber;
    public GroupEntity(int GroupID,String GroupName,int GroupContributionPoint,int UsedGroupContributionPoist,Date CreateTime) {
        this.GroupID = GroupID;
        this.GroupName = GroupName;
        this.GroupContributionPoint=GroupContributionPoint;
        this.UsedGroupContributionPoist = UsedGroupContributionPoist;
        this.CreateTime = CreateTime;
        SetGroupVaule();
    }

    public String getGroupName() {
        return GroupName;
    }

    private void SetGroupVaule(){
        int lastlevel=0;
        for(Map.Entry<Integer,String[]> TempLevel:GroupConfig.GroupLevel.entrySet()){
            if (UsedGroupContributionPoist<Integer.parseInt(TempLevel.getValue()[0])){
                GroupLevel = TempLevel.getKey()-1;
            }
            MaxHuManNumber = Integer.parseInt(TempLevel.getValue()[2]);
        }
        GroupLevel = lastlevel;
    }

    public int getMaxHuManNumber() {
        return MaxHuManNumber;
    }

    public int GetGroupLevel(){return GroupLevel;}

    public int getGroupID() {
        return GroupID;
    }

    public int getGroupContributionPoint() {
        return GroupContributionPoint;
    }

    public int getUsedGroupContributionPoist() {
        return UsedGroupContributionPoist;
    }

    public Date getCreateTime() {
        return CreateTime;
    }
}
