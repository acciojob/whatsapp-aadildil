package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WhatsappService {

WhatsappRepository whatsappRepositor=new WhatsappRepository();

    public void createUser(String name, String mobile) throws Exception {


         if(  whatsappRepositor.findUserByNumber(mobile))
             throw new Exception("User already exists");
         User user=new User(name,mobile);
         whatsappRepositor.addUser(user);
    }

    public Group createGroup(List<User> users) {

        int n=users.size();
        if(n==2)
            return whatsappRepositor.createPersonalChat(users);
        else
            return whatsappRepositor.createGroup(users);

    }

    public int createMessage(String content) {
        return whatsappRepositor.createMessage(content);
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception {
        return whatsappRepositor.sendMessage(message,sender,group);
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception {
        return whatsappRepositor.changeAdmin(approver,user,group);
    }
}