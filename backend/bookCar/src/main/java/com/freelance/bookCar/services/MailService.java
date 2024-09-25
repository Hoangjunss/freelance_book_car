package com.freelance.bookCar.services;

import com.freelance.bookCar.dto.MessageDTO;
import com.freelance.bookCar.models.Mail;
import org.springframework.stereotype.Service;


@Service
public interface MailService {
    void sendMail(Mail mail);
    Mail getMail(String mailTo,String content,String subject);
    void  send(MessageDTO messageDTO);
}
