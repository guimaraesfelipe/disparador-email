package br.com.fgtech.disparador_email.service;

import br.com.fgtech.disparador_email.enums.StatusEmail;
import br.com.fgtech.disparador_email.model.EmailModel;
import br.com.fgtech.disparador_email.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailService {
    @Autowired
    EmailRepository emailRepository;
    @Autowired
    JavaMailSender mailSender;

    public EmailModel sendEmail(EmailModel emailModel) {
        emailModel.setSendDateEmail(LocalDateTime.now());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            mailSender.send(message);
            emailModel.setStatusEmail(StatusEmail.SENT);
        }catch(MailException ex) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
        }finally {
            return emailRepository.save(emailModel);
        }
    }

    public List<EmailModel> getAll() {
        if (emailRepository.findAll().isEmpty()){
            return null;
        }
        return emailRepository.findAll();
    }
}