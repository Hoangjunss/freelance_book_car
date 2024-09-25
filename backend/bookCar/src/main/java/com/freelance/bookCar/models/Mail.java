package com.freelance.bookCar.models;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data

@Builder
public class Mail
{
    private String mailFrom;
    private String mailTo;
    private String mailCc;
    private String mailBcc;


    private String mailSubject;
    private String mailContent;
    private String contentType;
    private List <Object> attachments;

    public Date getMailSendDate() {
        return new Date();
    }
}
