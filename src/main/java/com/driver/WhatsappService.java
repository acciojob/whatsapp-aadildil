package com.driver;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WhatsappService {
    WhatsappRepository whatsappRepository=new WhatsappRepository();
    public String createUser(String name, String mobile) {

        if(whatsappRepository.numberExist(mobile))
            return "User already exists";
        whatsappRepository.createUser(name,mobile);
        return "SUCCESS";
    }

    public Group createGroup(List<User> users) {
        int size= users.size();
        if(size==2)
            return whatsappRepository.createPersonalChat(users);

        return whatsappRepository.createGroup(users);

    }

    public int createMessage(String content) {
        return whatsappRepository.createMessage(content);
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception {
        if(!whatsappRepository.groupExist(group))
            throw new Exception("Group does not exist");
        if(!whatsappRepository.userNotInGroup(sender,group))
            throw new Exception("You are not allowed to send message");
        return whatsappRepository.sendMessage(message,sender,group);
    }


    public boolean isAdmin(Group group, User user) {
        User admin=whatsappRepository.getAdmin(group);
        if(admin.equals(user))
            return true;
        return false;
    }

    public boolean userInTheGroup(User sender,Group group) {
        return whatsappRepository.userNotInGroup(sender,group);
    }
    public boolean checkGroupExist(Group group) {
        return whatsappRepository.groupExist(group);
    }

    public void changeAdmin(User user, Group group) {
        whatsappRepository.changeAdmin(user,group);
    }
}
