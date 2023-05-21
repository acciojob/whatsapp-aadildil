package com.driver;

import java.util.Date;

public class Message {
    private int id;
    private String content;
    private Date timestamp;


    public Message(String content,int id) {
        this.content = content;
        this.id=id;
        timestamp=new Date();

    }


}
