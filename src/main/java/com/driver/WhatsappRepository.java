package com.driver;


import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group



    private HashMap<Group, List<User>> groupUserMap;

    private HashMap<Group, List<Message>> groupMessageMap;

    private HashMap<Message, User> senderMap;

    private HashMap<Group, User> adminMap;

    private HashMap<String,User> mobileUserMap;

    private HashMap<Integer, Message> messageMap;

    private  int customGroupCount;

    private  int messageCount;



    public WhatsappRepository() {
        this.groupUserMap=new HashMap<>();
        this.groupMessageMap=new HashMap<>();
        this.senderMap=new HashMap<>();
        this.adminMap=new HashMap<>();
        this.mobileUserMap=new HashMap<>();
        this.messageMap=new HashMap<>();

    }

    public boolean findUserByNumber(String mobile) {
        if(mobileUserMap.containsKey(mobile))
            return true;
        return false;

    }

    public void addUser(User user) {
        mobileUserMap.put(user.getMobile(), user);
    }

    public Group createPersonalChat(List<User> users) {
        User admin= users.get(0);
        String name= users.get(1).getName();
        Group group=new Group(name,2);

        groupUserMap.put(group,users);
        adminMap.put(group,admin);
        return group;
    }

    public Group createGroup(List<User> users) {
        int count=users.size();

       customGroupCount++;
       String name="Group "+ customGroupCount;
        User admin=users.get(0);

        Group group=new Group(name,count);
        adminMap.put(group,admin);
        groupUserMap.put(group,users);

        return group;
    }

    public int createMessage(String content) {
        Message message= new Message(++messageCount,content);
        messageMap.put(messageCount,message);
        return messageCount;
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception {
        if(!groupUserMap.containsKey(group))
            throw new Exception("Group does not exist");
        List<User> userList=groupUserMap.get(group);
        if(!userList.contains(sender))
            throw new Exception("You are not allowed to send message");
        List<Message> messageList=new ArrayList<>();
        if(groupMessageMap.containsKey(group))
            messageList=groupMessageMap.get(group);
        messageList.add(message);
        groupMessageMap.put(group,messageList);
                senderMap.put(message,sender);
        return messageList.size();
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception {
        if(!groupUserMap.containsKey(group))
            throw new Exception("Group does not exist");
        List<User> userList=groupUserMap.get(group);
        if(!userList.contains(user))
            throw new Exception("User is not a participant");

        if(!adminMap.get(group).equals(approver))
            throw new Exception("Approver does not have rights");

        adminMap.put(group,user);
        return "SUCCESS";

    }
}