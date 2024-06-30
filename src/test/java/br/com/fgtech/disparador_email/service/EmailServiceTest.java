package br.com.fgtech.disparador_email.service;

import br.com.fgtech.disparador_email.enums.StatusEmail;
import br.com.fgtech.disparador_email.model.EmailModel;
import br.com.fgtech.disparador_email.repository.EmailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;

class EmailServiceTest {

    @InjectMocks
    EmailService emailService;

    @Mock
    EmailRepository emailRepository;

    @Mock
    JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    String fromMessage;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Executes the triggering of a successful email")
    void MustReturnSuccessForRequest() {
        EmailModel emailModel = new EmailModel();
        emailModel.setEmailFrom(fromMessage);
        emailModel.setEmailTo("qa@fgtech.com.br");
        emailModel.setSubject("Unit Test Service");
        emailModel.setText("Message");
        emailModel.setOwnerRef("administrator");

        assertDoesNotThrow(() -> emailService.sendEmail(emailModel));
        assertEquals(emailModel.getStatusEmail(), StatusEmail.SENT);
    }

}