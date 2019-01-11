package com.qq44920040.Minecarft.SuperGroup;


import java.util.HashMap;
import java.util.Map;

public class GroupConfig {
    public static Map<Integer,String[]> GroupLevel=new HashMap<>();
    static int Vice_President;
    static int Elite;
    static int EnterGroupTimeSetPosition;
    public static String GroupCardLore;
    static String[] Donation={"100000","200000","300000"};
    public static String[] SuperGroupInfo = {"§4=======SuperGroup=======","§41.[任何人]查看服务器公会列表/SG list","§42.[任何人]查看自己公会/SG SG","§43.[任何人]创建公会/SG create 公会名","§44.[精英以上]同意入会/SG accept 玩家名","§45.[副会以上]踢出公会成员/SG kick 玩家名","§46.[会长]设置1副会/2精英职位、修改公会名/SG set 1 玩家名","§47.[会长]升级公会等级/SG levelup"};
    public static String SuperGroupListTitle = "§d公会列表";
    public static String[] SGlistItem =new String[]{"公会名:[SuperGroupName]","公会等级:[SuperGroupLevel]","人数:[SuperGroupNewNum]/[SuperGroupMaxNum]","会长:[oderName]","编号:[GroupKeyId]"};
    public static String PageUpButtonDisPlay = "上一页";
    public static String PageDownButtonDisPlay = "下一页";
}
