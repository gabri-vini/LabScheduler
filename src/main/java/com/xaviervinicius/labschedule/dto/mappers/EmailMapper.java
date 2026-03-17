package com.xaviervinicius.labschedule.dto.mappers;

import com.mailgun.model.message.Message;
import com.xaviervinicius.labschedule.dto.SimpleEmail;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;

@UtilityClass
public class EmailMapper {
    public SimpleEmail map(Message msg){
        boolean isHtml = msg.getHtml() != null;
        return new SimpleEmail(
                new ArrayList<>(msg.getTo()),
                msg.getFrom(),
                msg.getSubject(),
                isHtml ? msg.getHtml() : msg.getText(),
                isHtml
        );
    }

    public Message map(SimpleEmail email){
        Message.MessageBuilder builder = Message.builder()
                .to(email.to())
                .from(email.from())
                .subject(email.subject());

        if(email.isHtml()){
            builder.html(email.body());
        }else{
            builder.text(email.body());
        }

        return builder.build();
    }
}
