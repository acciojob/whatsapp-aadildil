package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below-mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;
    private static int customGroupCount=0;
    private static int messageId=0;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();//user datatabase for groups
        this.senderMap = new HashMap<Message, User>();//sent by databse
        this.adminMap = new HashMap<Group, User>();//admin map
        this.userMobile = new HashSet<>();//user map for mobile (primary key)
        this.customGroupCount = 0;
        this.messageId = 0;
    }


    public boolean checkUserExist(String mobile) {
        if(userMobile.contains(mobile))
            return true;
        return false;
    }

    public String createUser(String name, String mobile) {
        User user=new User(name,mobile);
        userMobile.add(mobile);
        return "SUCCESS";
    }

    public Group createPersonalChat(List<User> users) {


        User admin=users.get(0);
        String name=users.get(1).getName();

        Group group=new Group(name,2);
        groupUserMap.put(group,users);
        adminMap.put(group,admin);
        return group;
    }

    public Group createGroup(List<User> users) {

        // If there are 2+ users, the name of group should be "Group count".
        // For example, the name of first group would be "Group 1", second would be "Group 2" and so on.
        ++customGroupCount;
        String name="Group "+(String.valueOf(customGroupCount));
        User admin=users.get(0);
        int size=users.size();

        Group group=new Group(name,size);
        groupUserMap.put(group,users);
        adminMap.put(group,admin);
        return group;
    }

    public int createMessage(String content) {
        ++messageId;
        Message message=new Message(content,messageId);
        return messageId;
    }

    public boolean checkGroupExist(Group group) {
        if(groupUserMap.containsKey(group))
            return true;
        return false;
    }

    public boolean isUserInTheGroup(User sender, Group group) {
        List<User> users=groupUserMap.get(group);
        if(users!=null&&users.contains(sender))
            return true;
        return false;
    }

    public int sendMessage(Message message, User sender, Group group) {

        List<Message> messageList=groupMessageMap.get(group);
        messageList.add(message);

        senderMap.put(message,sender);

        return groupMessageMap.get(group).size();
    }

    public boolean notAdminOfGroup(Group group, User approver) {
        if(adminMap.get(group).equals(approver))
            return true;
        return false;

    }

    public String changeAdmin(User approver, Group group, User user) {
        adminMap.remove(group);
        adminMap.put(group,user);
        return "SUCCESS";
    }
}
