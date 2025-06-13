package com.huynhduc.backend.controller.MailController;

import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

@RestController
@RequestMapping("/apply-job")
public class MailController {

    @Autowired
    private JavaMailSender mailSender;

    @Value("classpath:templates/apply-email.html")
    private Resource emailTemplate;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> appliedToRecruiter(
            @RequestParam("full_name") String fullName,
            @RequestParam("phone_number") String phoneNumber,
            @RequestParam("job_name") String jobName,
            @RequestParam("from_email") String fromEmail,
            @RequestParam("to_email") String toEmail,
            @RequestParam("url_view_cv") String urlViewCV,
            @RequestParam("avatar") String avatar,
            @RequestParam("url_logo") String urlLogo,
            @RequestParam("url_web") String urlWeb,
            @RequestParam("url") String profileUrl
    ) {
        try {
            String template = Files.readString(emailTemplate.getFile().toPath(), StandardCharsets.UTF_8);

            Map<String, String> replacements = Map.of(
                    "full_name", fullName,
                    "phone_number", phoneNumber,
                    "job_name", jobName,
                    "email", fromEmail,
                    "url_view_cv", urlViewCV,
                    "avatar", avatar,
                    "url_logo", urlLogo,
                    "url_web", urlWeb,
                    "url", profileUrl
            );

            for (Map.Entry<String, String> entry : replacements.entrySet()) {
                template = template.replace("{{ " + entry.getKey() + " }}", entry.getValue());
            }

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("noreply@joblink.com.vn");
            helper.setTo(toEmail);
            helper.setSubject("Ứng viên mới: " + fullName + " - " + jobName);
            helper.setText(template, true);

            mailSender.send(message);

            return ResponseEntity.ok("Email sent successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to send email: " + e.getMessage());
        }
    }
}
