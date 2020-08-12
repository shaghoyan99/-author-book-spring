package am.itspace.service;

import am.itspace.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Async
    public void setMailSender(String to ,String subject, String message) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        mailSender.send(simpleMailMessage);
    }

    @Async
    public void setHtmlEmail(String to , String subject, User user, String link , String templateName, Locale locale) throws MessagingException {
        final Context ctx = new Context(locale);
        ctx.setVariable("user", user);
        ctx.setVariable("url", link);

        final String htmlContent = this.templateEngine.process(templateName, ctx);

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper message =
                new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
        message.setSubject(subject);
        message.setFrom("javaForTest2018@gmail.com");
        message.setTo(to);
        message.setText(htmlContent, true); // true = isHtml
        // Send mail
        this.mailSender.send(mimeMessage);
    }
}
