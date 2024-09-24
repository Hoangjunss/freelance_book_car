package com.freelance.bookCar.services;


import com.freelance.bookCar.dto.MailDTO;
import com.freelance.bookCar.dto.MessageDTO;
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
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();// tao thu vien ho tro
        try {
            log.info("Sending mail to email: {}", mail.getMailFrom());
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(mail.getMailSubject()); // tiêu đề
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom())); // ai gửi
            mimeMessageHelper.setTo(mail.getMailTo()); // gửi ai
            mimeMessageHelper.setText(mail.getMailContent()); // nội dung
            javaMailSender.send(mimeMessageHelper.getMimeMessage()); // thư viện hỗ trợ gửi mail
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Mail getMail(String mailTo, String content, String subject) {
        return Mail.builder()
                .mailTo(mailTo)
                .mailSubject(subject)
                .mailContent(content)
                .mailFrom("cvreviewbaconbao@gmail.com")
                .contentType("text/plain")
                .build();
    }

    @Override
    public void send(MessageDTO messageDTO) {
        log.info("send mail");
        log.info("send mail: {}", messageDTO.getId());
        MailDTO mailDTO = MailDTO.builder()
                .mailContent(messageDTO.getMessage())
                .mailSubject(messageDTO.getMessage())
                .mailTo(messageDTO.getMessage())

                .build();
        sendMail(getMail(mailDTO.getMailTo(), mailDTO.getMailContent(), mailDTO.getMailSubject()));
    }
}
