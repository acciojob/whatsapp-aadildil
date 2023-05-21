package com.driver;

import java.util.List;

public class WhatsappService {
    WhatsappRepository whatsappRepository=new WhatsappRepository();



    public String createUser(String name, String mobile) throws Exception {
        if(checkUserExist(mobile))
        {
            throw new Exception("User already exists");
        }
        return whatsappRepository.createUser(name,mobile);
    }

    public boolean checkUserExist(String mobile) {
        return whatsappRepository.checkUserExist(mobile);
    }


    public Group createGroup(List<User> users) {

        if(users.size()==2)
        {
            // If there are only 2 users, the group is a personal chat and the group name should be kept as the name of the second user(other than admin)
           return whatsappRepository.createPersonalChat(users);
        }
        else
            return whatsappRepository.createGroup(users);

    }

    public int createMessage(String content) {
        return whatsappRepository.createMessage(content);


    }

    public boolean checkGroupExist(Group group) {
        return whatsappRepository.checkGroupExist(group);
    }

    public boolean userInTheGroup(User sender, Group group) {
        return whatsappRepository.isUserInTheGroup(sender,group);
    }

    public int sendMessage(Message message, User sender, Group group) {
        return whatsappRepository.sendMessage(message,sender,group);
    }

    public boolean notAdminOfGroup(Group group, User approver) {
        return whatsappRepository.notAdminOfGroup(group,approver);
    }

    public String changeAdmin(User approver, Group group, User user) {
        return whatsappRepository.changeAdmin(approver,group,user);
    }
}
