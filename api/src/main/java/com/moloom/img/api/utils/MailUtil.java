package com.moloom.img.api.utils;

import com.moloom.img.api.to.Mail;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;


/**
 * 利用javamail来发送邮件
 * 网易邮箱授权码：QJZFOKIJHQSMPDAT
 * qq邮箱授权码：crplmuoslxnbebbb
 *
 * @author
 */
public class MailUtil {

    public static void sendEmail(Mail mail) throws Exception {

        // 1.创建一个程序与邮件服务器会话对象 Session
        Properties props = new Properties();
        // 设置邮件传输协议为SMTP
        props.setProperty("mail.transport.protocol", "SMTP");
        // 设置SMTP服务器地址
        props.setProperty("mail.host", "smtp.163.com");
        // 设置SMTP服务器是否需要用户验证，需要验证设置为true
        props.setProperty("mail.smtp.auth", "true");
        // 创建验证器
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("moxy520527@163.com", "QJZFOKIJHQSMPDAT");
            }
        };
        Session session = Session.getInstance(props, auth);
        // 2.创建一个Message，它相当于是邮件内容
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("moxy520527@163.com")); // 设置发送者
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mail.getAddressee())); // 设置发送方式与接收者
        message.setSubject(mail.getSubject());
        message.setContent(mail.getText(), "text/html;charset=utf-8");
        // 3.创建 Transport用于将邮件发送
        Transport.send(message);


/*
            // 1.创建一个程序与邮件服务器会话对象 Session
            Properties props = new Properties();
            // 设置邮件传输协议为SMTP
            props.setProperty("mail.transport.protocol", "SMTP");
            // 设置SMTP服务器地址
            props.setProperty("mail.host", "smtp.qq.com");
            // 设置SMTP服务器是否需要用户验证，需要验证设置为true
            props.setProperty("mail.smtp.auth", "true");
            //QQ存在一个特性设置SSL加密
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);
            // 创建验证器
            Authenticator auth = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("2287993381@qq.com", "crplmuoslxnbebbb");
                }
            };
            Session session = Session.getInstance(props, auth);
            // 2.创建一个Message，它相当于是邮件内容
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("2287993381@qq.com")); // 设置发送者
            message.setRecipient(RecipientType.TO, new InternetAddress(mail.getAddressee())); // 设置发送方式与接收者
            message.setSubject(mail.getSubject());
            message.setContent(mail.getText(), "text/html;charset=utf-8");
            // 3.创建 Transport用于将邮件发送
            Transport.send(message);
 */
    }


}
