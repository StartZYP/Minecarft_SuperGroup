package com.qq44920040.Minecarft.SuperGroup.DUtil;

import com.qq44920040.Minecarft.SuperGroup.Entity.GroupEntity;
import com.qq44920040.Minecarft.SuperGroup.Entity.PlayerEntity;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class DaoTool {
    private static Connection connection = null;
    private static Statement statement = null;
    public DaoTool(String DatabasePath){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:"+DatabasePath+".db");
            statement = connection.createStatement();
            String SuperGroupTable = "CREATE TABLE IF NOT EXISTS 'SuperGroup'( SuperGroupId INTEGER PRIMARY KEY, SuperGroupName VARCHAR(200), GroupContributionPoint INTEGER, UsedGroupContributionPoist integer, SuperGroupCreateTime DATETIME )";
            statement.executeUpdate(SuperGroupTable);
            String PlayerTable = "CREATE TABLE IF NOT EXISTS 'SuperGroupPlayer'( SuperGroupId INTEGER, PlayerUUid VARCHAR(100), HaveContributionPoint INTEGER, PostionType integer, GroupJoinTime DATETIME )";
            statement.executeUpdate(PlayerTable);
            String GroupLog = "CREATE TABLE IF NOT EXISTS 'SuperGroupLog'( SuperGroupId INTEGER, PlayerUUid VARCHAR(100),SuperGroupCreateTime DATETIME)";
            statement.executeUpdate(GroupLog);
            statement.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static void GroupLogRemovePlayer(int SuperGroupId,UUID playeruuid){
        if (SuperGroupId==-1){
            try {
                connection.setAutoCommit(false);
                statement = connection.createStatement();
                String sql = "DELETE FROM SuperGroupLog where PlayerUUid="+ playeruuid;
                statement.executeUpdate(sql);
                connection.commit();
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }else {
            try {
                connection.setAutoCommit(false);
                statement = connection.createStatement();
                String sql = "DELETE FROM SuperGroupLog where PlayerUUid="+ playeruuid+" and SuperGroupId="+SuperGroupId;
                statement.executeUpdate(sql);
                connection.commit();
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }


    public static ArrayList<GroupEntity> GetPageSuperGroup(int Pagenum){
        Pagenum*=27;
        ArrayList<GroupEntity> SuperGroupList=new ArrayList<>();
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String GetSuperGroupSql = "select * from SuperGroup order by SuperGroupCreateTime limit 27 offset "+Pagenum;
            ResultSet rs = statement.executeQuery(GetSuperGroupSql);
            while (rs.next()){
                int GroupID = rs.getInt("SuperGroupId");
                String SuperGroupName = rs.getString("SuperGroupName");
                int GroupContributionPoint = rs.getInt("GroupContributionPoint");
                int UsedGroupContributionPoist = rs.getInt("UsedGroupContributionPoist");
                Date SuperGroupCreateTime = rs.getDate("SuperGroupCreateTime");
                SuperGroupList.add(new GroupEntity(GroupID,SuperGroupName,GroupContributionPoint,UsedGroupContributionPoist,SuperGroupCreateTime));
            }
            rs.close();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return SuperGroupList;
    }

    public static ArrayList<PlayerEntity> GetPlayerEntity(int SuperGroupId){
        ArrayList<PlayerEntity> PlayerEntityList = new ArrayList<>();
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql = "select * from SuperGroupPlayer where SuperGroupId="+SuperGroupId;
            ResultSet rs =statement.executeQuery(sql);
            if(rs.next()){
                String PlayerUUid = rs.getString("PlayerUUid");
                int HaveContributionPoint = rs.getInt("HaveContributionPoint");
                int PostionType = rs.getInt("PostionType");
                PlayerEntityList.add(new PlayerEntity(PlayerUUid,HaveContributionPoint,PostionType));
            }
            connection.commit();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return PlayerEntityList;
    }



    public static UUID GetGroupOder(int SuperGroupId){
        String PlayerUUid="1";
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql = "select * from SuperGroupPlayer where SuperGroupId="+SuperGroupId+"and PostionType=1";
            ResultSet rs =statement.executeQuery(sql);
            if(rs.next()){
                PlayerUUid = rs.getString("PlayerUUid");
            }
            connection.commit();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return UUID.fromString(PlayerUUid);
    }

    public static int GetPlayerHaveGroup(UUID UUid){
        int haveGroup=-1;
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql = "select * from SuperGroupPlayer where PlayerUUid='"+UUid+"'";
            ResultSet rs =statement.executeQuery(sql);
            if (rs.next()){
                rs.getInt("SuperGroupId");
            }
            connection.commit();
            rs.close();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return haveGroup;
    }

    public static int GetHumanNum(int SuperGroupId){
        int tempnum = 0;
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String GetSuperGroupSql = "select * from SuperGroupPlayer where SuperGroupId="+SuperGroupId;
            ResultSet rs = statement.executeQuery(GetSuperGroupSql);
            while (rs.next()){
                tempnum++;
            }
            rs.close();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return tempnum;
    }

    public static boolean GetGroupJoinLogHas(String SuperGroupId,UUID playeruuid){
        int tempnum = -1;
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String GetSuperGroupSql = "select * from SuperGroupLog where SuperGroupId="+SuperGroupId+"and PlayerUUid="+playeruuid;
            ResultSet rs = statement.executeQuery(GetSuperGroupSql);
            while (rs.next()){
                tempnum++;
            }
            rs.close();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return tempnum!=-1;
    }

    public static ArrayList<String> GetGroupJoinLogMsg(int SuperGroupId){
        ArrayList<String> PlayerList = new ArrayList<>();
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String GetSuperGroupSql = "select * from SuperGroupLog where SuperGroupId="+SuperGroupId;
            ResultSet rs = statement.executeQuery(GetSuperGroupSql);
            while (rs.next()){
                PlayerList.add(rs.getString("PlayerUUid"));
            }
            rs.close();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return PlayerList;
    }

    public static void AddGroupJoinLog(int SuperGroupId,UUID playeruuid){
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql = "INSERT INTO SuperGroupLog (SuperGroupId,PlayerUUid,SuperGroupCreateTime)values("+SuperGroupId+",'"+playeruuid+"',datetime('now','localtime'))";
            statement.executeUpdate(sql);
            connection.commit();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void AddGroupPlayer(int SuperGroupId,UUID playeruuid,int PostionType){
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql = "INSERT INTO SuperGroupPlayer (SuperGroupId,PlayerUUid,HaveContributionPoint,PostionType,GroupJoinTime)values("+SuperGroupId+",'"+playeruuid+"',0,"+PostionType+",datetime('now','localtime'))";
            statement.executeUpdate(sql);
            connection.commit();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static int AddSuperGroup(String SuperGroupName){
        int KeyId=-1;
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql = "INSERT INTO SuperGroup (SuperGroupName,GroupContributionPoint,UsedGroupContributionPoist,SuperGroupCreateTime)values('"+SuperGroupName+"',0,0,datetime('now','localtime'))";
            statement.executeUpdate(sql);
            ResultSet rs =statement.getGeneratedKeys();
            if(rs.next()){
               KeyId = rs.getInt(1);
            }
            connection.commit();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return KeyId;
    }
}
