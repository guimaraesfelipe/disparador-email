package br.com.fgtech.disparador_email.controller;

import br.com.fgtech.disparador_email.dto.EmailDto;
import br.com.fgtech.disparador_email.model.EmailModel;
import br.com.fgtech.disparador_email.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmailController {
    @Autowired
    EmailService emailService;

    @Operation(description = "Dispara um email e registra o log de envio no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping("/sending-email")
    public ResponseEntity<EmailModel> sendingEmail(@RequestBody @Valid EmailDto emailDto) {
        EmailModel emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDto, emailModel);
        emailService.sendEmail(emailModel);
        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);
    }

    @Operation(description = "Retorna todos os emails já disparados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @GetMapping("/emails")
    public ResponseEntity<?> getAll(){
        List<EmailModel> emails = emailService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body((emails != null ? emails:"Nenhum email encontrado"));
    }

}