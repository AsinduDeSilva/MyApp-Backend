package com.example.myapp.controller;

import com.example.myapp.dto.MailDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @PostMapping("/send")
    public String sendEmail(@RequestBody MailDetailsDTO mailDetailsDTO){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(mailDetailsDTO.getSubject());
            message.setTo(mailDetailsDTO.getTo());
            message.setFrom(sender);
            message.setText(mailDetailsDTO.getMessage());

            javaMailSender.send(message);
            return "Success";
        }catch (Exception ex){
            return ex.getMessage();
        }
    }

    @PostMapping("/sendWithAttachment")
    public String sendEmail(@RequestParam(value = "attachments") MultipartFile[] files,
                            @RequestParam(value = "to") String to, @RequestParam(value = "subject") String subject,
                            @RequestParam(value = "message") String message) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setText(message);
            mimeMessageHelper.setSubject(subject);
            for (MultipartFile file : files) {
                mimeMessageHelper.addAttachment(file.getOriginalFilename(), file);
            }
            javaMailSender.send(mimeMessage);
            return "Success";
        }catch (MessagingException ex){
            return ex.getMessage();
        }

    }
}
