package org.shine.common.utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Date;
import java.util.Properties;

/**
 *
 */
public class SendMailUtil {
	
	private static String EMAIL_HOST;
	private static String EMAIL_LOCALHOST;
	private static String EMAIL_SENDER;
	private static String EMAIL_SENDERPASSWD;
	private static boolean EMAIL_DEBUG;
	
	private static SendMailUtil sendMailUtil;
	
	private SendMailUtil() {
		
	}
	
	public SendMailUtil getInstance() {
		if(sendMailUtil == null) {
			synchronized (SendMailUtil.class) {
				if (sendMailUtil == null) {
					sendMailUtil = new SendMailUtil();
				}
			}
		}
		return sendMailUtil;
	}
	
	public static void init(String host, String localhost, String sender, String senderPassword, boolean debug) {
		EMAIL_HOST = host;
		EMAIL_LOCALHOST = localhost;
		EMAIL_SENDER = sender;
		EMAIL_SENDERPASSWD = senderPassword;
		EMAIL_DEBUG = debug;
	}

	@SuppressWarnings("restriction")
	public void sendSSLMessage(String recipients[], String subject,
			String message, String from) throws MessagingException,
			UnsupportedEncodingException {

		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

		Properties props = new Properties();
		props.put("mail.smtp.host", EMAIL_HOST);
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.smtp.localhost",EMAIL_LOCALHOST);
		// props.put("mail.smtp.port", SMTP_PORT);
		props.put("mail.smtp.socketFactory.port", 465);
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {

					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								EMAIL_SENDER,
								EMAIL_SENDERPASSWD);
					}
				});

		session.setDebug(EMAIL_DEBUG);

		Message msg = new MimeMessage(session);
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom);

		InternetAddress[] addressTo = new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++) {
			addressTo[i] = new InternetAddress(recipients[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);
		
		// 设置邮件消息的主题
		msg.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
		// 设置邮件消息发送的时间
		msg.setSentDate(new Date());
		// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
		Multipart mainPart = new MimeMultipart();
		// 创建一个包含HTML内容的MimeBodyPart
		BodyPart html = new MimeBodyPart();
		// 设置HTML内容
		html.setContent(message, "text/html; charset=UTF-8");
		mainPart.addBodyPart(html);
		// 将MiniMultipart对象设置为邮件内容
		msg.setContent(mainPart);
		// 发送邮件
		Transport.send(msg);
	}
}