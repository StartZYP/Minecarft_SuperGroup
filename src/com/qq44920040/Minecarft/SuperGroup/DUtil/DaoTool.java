package com.qq44920040.Minecarft.SuperGroup.DUtil;

import com.qq44920040.Minecarft.SuperGroup.Entity.GroupEntity;
import com.qq44920040.Minecarft.SuperGroup.Entity.PlayerEntity;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            String SuperGroupTable = "CREATE TABLE IF NOT EXISTS 'SuperGroup'( SuperGroupId INTEGER PRIMARY KEY, SuperGroupName VARCHAR(100), GroupContributionPoint INTEGER, UsedGroupContributionPoist integer, SuperGroupCreateTime DATETIME )";
            statement.executeUpdate(SuperGroupTable);
            String PlayerTable = "CREATE TABLE IF NOT EXISTS 'SuperGroupPlayer'( SuperGroupId INTEGER, PlayerUUid VARCHAR(200), HaveContributionPoint INTEGER, PostionType integer, GroupJoinTime DATETIME )";
            statement.executeUpdate(PlayerTable);
            String GroupLog = "CREATE TABLE IF NOT EXISTS 'SuperGroupLog'( SuperGroupId INTEGER, PlayerUUid VARCHAR(200),SuperGroupCreateTime DATETIME)";
            statement.executeUpdate(GroupLog);
            statement.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    public static void UpdatePlayerContributionPoint(UUID playeruuid,int Contribution,boolean IsTake){
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql;
            if (IsTake){
                sql = "UPDATE SuperGroupPlayer set HaveContributionPoint=HaveContributionPoint-"+Contribution+"  where PlayerUUid='"+playeruuid+"'";
            }else {
                sql = "UPDATE SuperGroupPlayer set HaveContributionPoint=HaveContributionPoint+"+Contribution+" where PlayerUUid='"+playeruuid+"'";
            }
            statement.executeUpdate(sql);
            connection.commit();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void UpdatePlayerPostion(UUID playeruuid,boolean IsUp){
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql;
            if (IsUp){
                sql = "UPDATE SuperGroupPlayer set PostionType=PostionType-1 where PlayerUUid='"+playeruuid+"'";
            }else {
                sql = "UPDATE SuperGroupPlayer set PostionType=PostionType+1 where PlayerUUid='"+playeruuid+"'";
            }
            statement.executeUpdate(sql);
            connection.commit();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void GroupRemovePlayer(int SuperGroupId,UUID playeruuid,boolean IsDeleteAll){
        if (IsDeleteAll){
            try {
                connection.setAutoCommit(false);
                statement = connection.createStatement();
                String sql = "DELETE FROM SuperGroupPlayer where SuperGroupId="+SuperGroupId;
                statement.executeUpdate(sql);
                sql = "DELETE FROM SuperGroup where SuperGroupId="+SuperGroupId;
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
                String sql = "DELETE FROM SuperGroupPlayer where PlayerUUid='"+ playeruuid+"'";
                statement.executeUpdate(sql);
                connection.commit();
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public static void UpdateUsedGroupContribution(int GroupId,int Contribution,boolean IsTake){
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql;
            if (IsTake){
                sql = "UPDATE SuperGroup set UsedGroupContributionPoist=UsedGroupContributionPoist-"+Contribution+" where SuperGroupId="+GroupId;
            }else {
                sql = "UPDATE SuperGroup set UsedGroupContributionPoist=UsedGroupContributionPoist+"+Contribution+" where SuperGroupId="+GroupId;
            }
            statement.executeUpdate(sql);
            connection.commit();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void UpdateGroupContribution(int GroupId,int Contribution,boolean IsTake){
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql;
            if (IsTake){
                sql = "UPDATE SuperGroup set GroupContributionPoint=GroupContributionPoint-"+Contribution+" where SuperGroupId="+GroupId;
            }else {
                sql = "UPDATE SuperGroup set GroupContributionPoint=GroupContributionPoint+"+Contribution+" where SuperGroupId="+GroupId;
            }
            statement.executeUpdate(sql);
            connection.commit();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void UpdatePlayerContribution(UUID playeruuid,int Contribution,boolean IsTake){
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql;
            if (IsTake){
                sql = "UPDATE SuperGroupPlayer set HaveContributionPoint=HaveContributionPoint-"+Contribution+",GroupJoinTime=datetime('now','localtime') where PlayerUUid='"+playeruuid+"'";
            }else {
                sql = "UPDATE SuperGroupPlayer set HaveContributionPoint=HaveContributionPoint+"+Contribution+",GroupJoinTime=datetime('now','localtime') where PlayerUUid='"+playeruuid+"'";
            }
            statement.executeUpdate(sql);
            connection.commit();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void GroupLogRemovePlayer(int SuperGroupId,UUID playeruuid){
        if (SuperGroupId==-1){
            try {
                connection.setAutoCommit(false);
                statement = connection.createStatement();
                String sql = "DELETE FROM SuperGroupLog where PlayerUUid='"+ playeruuid+"'";
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
                String sql = "DELETE FROM SuperGroupLog where PlayerUUid='"+ playeruuid+"' and SuperGroupId="+SuperGroupId;
                statement.executeUpdate(sql);
                connection.commit();
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }


    public static GroupEntity GetSuperGroup(int SuperGroupid){
        GroupEntity SuperGroup=null;
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String GetSuperGroupSql = "select * from SuperGroup where SuperGroupId="+SuperGroupid;
            ResultSet rs = statement.executeQuery(GetSuperGroupSql);
            while (rs.next()){
                int GroupID = rs.getInt("SuperGroupId");
                String SuperGroupName = rs.getString("SuperGroupName");
                int GroupContributionPoint = rs.getInt("GroupContributionPoint");
                int UsedGroupContributionPoist = rs.getInt("UsedGroupContributionPoist");
                Date SuperGroupCreateTime = rs.getDate("SuperGroupCreateTime");
                SuperGroup = new GroupEntity(GroupID,SuperGroupName,GroupContributionPoint,UsedGroupContributionPoist,SuperGroupCreateTime);
            }
            rs.close();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return SuperGroup;
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
            while (rs.next()){
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

    public static PlayerEntity GetPlayerEntity(UUID PlayerUUID){
        PlayerEntity PlayerEntity=null;
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql = "select * from SuperGroupPlayer where PlayerUUid='"+PlayerUUID+"'";
            ResultSet rs =statement.executeQuery(sql);
            while (rs.next()){
                int HaveContributionPoint = rs.getInt("HaveContributionPoint");
                int PostionType = rs.getInt("PostionType");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date EnterDate = new Date();
                try{
                    EnterDate = sdf.parse(rs.getString("GroupJoinTime"));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                PlayerEntity = new PlayerEntity(HaveContributionPoint,PostionType,EnterDate);
            }
            connection.commit();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return PlayerEntity;
    }

    public static UUID GetGroupOder(int SuperGroupId){
        String PlayerUUid="1";
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql = "select PlayerUUid from SuperGroupPlayer where SuperGroupId="+SuperGroupId+" and PostionType=1";
            ResultSet rs =statement.executeQuery(sql);
            while (rs.next()){
                PlayerUUid = rs.getString("PlayerUUid");
            }
            rs.close();
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
                haveGroup= rs.getInt("SuperGroupId");
            }
            rs.close();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return haveGroup;
    }

    public static int GetHumanNum(int SuperGroupId,int TypePostion){
        int tempnum = 0;
        if (TypePostion==-1){
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
        }else {
            try {
                connection.setAutoCommit(false);
                statement = connection.createStatement();
                String GetSuperGroupSql = "select * from SuperGroupPlayer where SuperGroupId="+SuperGroupId+" and PostionType="+TypePostion;
                ResultSet rs = statement.executeQuery(GetSuperGroupSql);
                while (rs.next()){
                    tempnum++;
                }
                rs.close();
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return tempnum;
    }

    public static boolean GetGroupJoinLogHas(String SuperGroupId,UUID playeruuid){
        int tempnum = -1;
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String GetSuperGroupSql = "select * from SuperGroupLog where SuperGroupId="+SuperGroupId+" and PlayerUUid='"+playeruuid+"'";
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
