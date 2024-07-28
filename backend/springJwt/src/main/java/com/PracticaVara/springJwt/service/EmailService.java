package com.PracticaVara.springJwt.service;

import com.PracticaVara.springJwt.model.Account.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private JwtService jwtService;

    public void sendHtmlCodeEmail(User user, String code) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("niculai614@gmail.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, user.getEmail());
        message.setSubject("Resetare parola contului " + user.getUsername());
        String htmlTemplate = null;
        try {
            htmlTemplate = readFile("D:\\Proiect\\practica-2024\\backend\\springJwt\\src\\main\\resources\\templates\\forgot-password-template.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String htmlContent = htmlTemplate.replace("${firstName}", user.getFirstName());
        htmlContent = htmlContent.replace("${lastName}", user.getLastName());
        htmlContent = htmlContent.replace("${username}", user.getUsername());
        htmlContent = htmlContent.replace("${code}", code);

        message.setContent(htmlContent, "text/html; charset=utf-8");

        mailSender.send(message);
    }

    public void sendHtmlVerificationEmail(User user) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("niculai614@gmail.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, user.getEmail());
        message.setSubject("Confirmare email asociat contului " + user.getUsername());
        String htmlTemplate = null;
        String link = "http://localhost:4200/contul-meu/confirma-email?token=" + jwtService.generateToken(user) + "&email=" + user.getEmail();
        try {
            htmlTemplate = readFile("D:\\Proiect\\practica-2024\\backend\\springJwt\\src\\main\\resources\\templates\\confirm-email-template.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String htmlContent = htmlTemplate.replace("${firstName}", user.getFirstName());
        htmlContent = htmlContent.replace("${lastName}", user.getLastName());
        htmlContent = htmlContent.replace("${username}", user.getUsername());
        htmlContent = htmlContent.replace("${link}", link);

        message.setContent(htmlContent, "text/html; charset=utf-8");

        mailSender.send(message);
    }

    public String readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readString(path, StandardCharsets.UTF_8);
    }
}
