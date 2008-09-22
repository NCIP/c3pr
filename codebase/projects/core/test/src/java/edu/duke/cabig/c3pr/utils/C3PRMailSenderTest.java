package edu.duke.cabig.c3pr.utils;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import junit.framework.TestCase;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import edu.duke.cabig.c3pr.infrastructure.C3PRMailSenderImpl;

public class C3PRMailSenderTest extends TestCase {
	public void testJavaMaiSenderImpl() throws Exception {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPassword("semanticbits");
		mailSender.setPort(465);
		mailSender.setProtocol("smtps");
		mailSender
				.setUsername("c3prproject@gmail.com");
		Properties properties = new Properties();
		properties.put("mail.smtps.auth", "true");
		properties.put("mail.smtps.starttls.enable", "true");
		properties.put("mail.smtps.debug", "true");
		mailSender.setJavaMailProperties(properties);
		MimeMessage message = mailSender.createMimeMessage();
		message
				.setFrom(new InternetAddress(
						"biju.joseph.padupurackal@gmail.com"));
		message.setText("Welcome biju");
		message.setReplyTo(new InternetAddress[] { new InternetAddress(
				"c3prproject@gmail.com") });
		message.setRecipient(RecipientType.TO, new InternetAddress(
				"c3prproject@gmail.com"));
		message.setSubject("My mail via javamailsender");
		message.setDescription("Message description");
		message.setSentDate(new Date());
		mailSender.send(message);
	}

	public void testC3PRMaiSenderImpl() throws Exception {
		JavaMailSenderImpl mailSender = new C3PRMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPassword("semanticbits");
		mailSender.setPort(465);
		mailSender.setProtocol("smtps");
		mailSender
				.setUsername("c3prproject@gmail.com");
		Properties properties = new Properties();
		mailSender.setJavaMailProperties(properties);
		MimeMessage message = mailSender.createMimeMessage();
		message
				.setFrom(new InternetAddress(
						"biju.joseph.padupurackal@gmail.com"));
		message.setText("Welcome biju");
		message.setReplyTo(new InternetAddress[] { new InternetAddress(
				"c3prproject@gmail.com") });
		message.setRecipient(RecipientType.TO, new InternetAddress(
				"c3prproject@gmail.com"));
		message.setSubject("My mail via c3prmailsender");
		message.setDescription("Message description");
		message.setSentDate(new Date());
		mailSender.send(message);
	}
}