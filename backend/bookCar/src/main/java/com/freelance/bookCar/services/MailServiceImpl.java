package com.freelance.bookCar.services;


import com.freelance.bookCar.dto.MailDTO;
import com.freelance.bookCar.dto.MessageDTO;
import com.freelance.bookCar.exception.CustomException;
import com.freelance.bookCar.exception.Error;
import com.freelance.bookCar.models.Mail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMail(Mail mail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            log.info("Sending mail to email: {}", mail.getMailFrom());
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom()));
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setText(mail.getMailContent(), true);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            log.error("Failed to send mail", e);
            throw new CustomException(Error.MAIL_SENDING_FAILED);
        } catch (Exception e) {
            log.error("Invalid mail details", e);
            throw new CustomException(Error.MAIL_INVALID_DETAILS);
        }
    }


    @Override
    public Mail getMail(String mailTo, String content, String subject) {
        if (mailTo == null || mailTo.isEmpty()) {
            throw new CustomException(Error.MAIL_INVALID_MAILTO);
        }
        if (subject == null || subject.isEmpty()) {
            throw new CustomException(Error.MAIL_INVALID_CONTENT);
        }
        if (content == null || content.isEmpty()) {
            throw new CustomException(Error.MAIL_INVALID_SUBJECT);
        }
        return Mail.builder()
                .mailTo(mailTo)
                .mailSubject(subject)
                .mailContent(content)
                .mailFrom("cvreviewbaconbao@gmail.com")
                .contentType("text/plain")
                .build();
    }

    //Thieu username
    @Override
    public void send(MessageDTO messageDTO) {
        log.info("send mail: {}", messageDTO.getId());
        if(messageDTO.getMessage() == null){
            throw  new CustomException(Error.MAIL_INVALID_MESSAGE);
        }
        MailDTO mailDTO = MailDTO.builder()
                .mailContent(messageDTO.getMessage())
                .mailSubject(messageDTO.getMessage())
                .mailTo(messageDTO.getMessage())
                .build();
        try {
            sendMail(getMail(mailDTO.getMailTo(), mailDTO.getMailContent(), mailDTO.getMailSubject()));
        } catch (CustomException e) {
            log.error("Error occurred while sending mail: {}", e.getMessage());
            throw new CustomException(Error.MAIL_SENDING_FAILED);
        }
    }

}
