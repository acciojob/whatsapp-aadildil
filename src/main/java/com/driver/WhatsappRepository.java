package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;

    private HashMap<String, User> userMap;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public boolean numberExist(String mobile) {
        if(userMobile.contains(mobile))
            return true;
        return false;
    }

    public void createUser(String name, String mobile) {

        User user=new User(name,mobile);
        userMap.put(mobile,user);
        
        
    }

    public Group createPersonalChat(List<User> users) {

        String name=users.get(1).getName();
        Group group=new Group(name,2);
        User admin=users.get(0);
        
        groupUserMap.put(group,users);
        adminMap.put(group,admin);
        return group;
        
        
    }

    public Group createGroup(List<User> users) {

        String name="Group "+(++customGroupCount);
        Group group=new Group(name,users.size());

        User admin=users.get(0);

        groupUserMap.put(group,users);
        adminMap.put(group,admin);
        return group;
    }

    public int createMessage(String content) {
        Message message=new Message(content,messageId++);



        return message.getId();
    }

    public boolean groupExist(Group group) {
        if(groupUserMap.containsKey(group))
            return true;
        else
            return false;
    }

    public boolean userNotInGroup(User sender, Group group) {
        List<User> users=groupUserMap.get(group);
        if(users!=null&&users.contains(sender))
            return true;
        return false;
    }

    public int sendMessage(Message message, User sender, Group group) {
        List<Message> messageList=groupMessageMap.get(group);
        messageList.add(message);
        groupMessageMap.put(group,messageList);
        senderMap.put(message,sender);
        return messageList.size();
    }
    public User getAdmin(Group group) {
        return adminMap.get(group);
    }
    public void changeAdmin(User user, Group group) {
        adminMap.put(group,user);

    }
}
