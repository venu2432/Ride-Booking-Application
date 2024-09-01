package com.project.ridebooking.RideBookingApplication.Service.Impl;

import com.project.ridebooking.RideBookingApplication.Entity.Ride;
import com.project.ridebooking.RideBookingApplication.Service.NotificationMailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationMailServiceImpl implements NotificationMailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void sendMail(String email, String subject, String body) {
        try{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setText(body);
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setFrom("your.ride.booking@gmail.com");
            simpleMailMessage.setCc("venumaripi7@gmail.com");
            javaMailSender.send(simpleMailMessage);
            log.info("Mail successfully send");
        }
        catch (Exception e){
            log.info("cannot send mail {}", e.getMessage());
        }

    }

    @Override
    public void sendEmail(String[] emails, String subject, String body) {
        try{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setText(body);
            simpleMailMessage.setTo(emails);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setFrom("your.ride.booking@gmail.com");
            simpleMailMessage.setCc("venumaripi7@gmail.com");
            javaMailSender.send(simpleMailMessage);
            log.info("Mail successfully send");
        }
        catch (Exception e){
            log.info("cannot send mail {}", e.getMessage());
        }

    }

    @Async
    @Override
    public void sendConfirmationMail(Ride createdRide) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a");
            String formattedDateTime = createdRide.getCreatedTime().format(formatter);
            Map<String,Object> rideDetails = new HashMap<>();
            rideDetails.put("rideId",createdRide.getId());
            rideDetails.put("rideDateTime", formattedDateTime);
            rideDetails.put("customerName",createdRide.getRider().getUser().getName());
            rideDetails.put("pickupLocation",createdRide.getPickUpLocation());
            rideDetails.put("dropoffLocation",createdRide.getDropOffLocation());

            Context context = new Context();
            context.setVariables(rideDetails);
            String htmlContent = templateEngine.process("Notification", context);

            helper.setText(htmlContent, true);
            helper.setSubject("Your ride is confirmed!!");
            helper.setFrom(new InternetAddress("your.ride.booking@gmail.com", "Ride Booking Application"));
            helper.setTo(createdRide.getRider().getUser().getEmail());

            javaMailSender.send(mimeMessage);
            log.info("HTML mail successfully sent to {}", createdRide.getRider().getUser().getEmail());
        } catch (MessagingException e) {
            log.error("Cannot send HTML mail: {}", e.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
