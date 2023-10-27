package com.harry.boostrap.startup.analyze.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class EmailHelper {
  @Value("${export.email.host}")
  private String emailHost;
  @Value("${export.email.from}")
  private String emailFrom;
  @Value("${export.email.password}")
  private String emailPassword;
  @Value("${export.email.port:25}")
  private int emailPort;

  public void sendEmail(String address, String title, String content)
      throws MessagingException, UnsupportedEncodingException {


    Properties properties = new Properties();
    properties.put("mail.smtp.host",emailHost);
    properties.put("mail.smtp.auth","true");
    Session session = Session.getInstance(properties);
    Message emailMessage = new MimeMessage(session);

    try {
      //发送人
      InternetAddress sender = new InternetAddress(emailFrom); //发送者账号
//      sender.setPersonal(MimeUtility.encodeText(emailInfo.getNickName()));//昵称
      emailMessage.setFrom(sender);

      //收件人
      InternetAddress to = new InternetAddress(address);//收件人账号
      emailMessage.setRecipient(Message.RecipientType.TO,to);

      //消息
      emailMessage.setSubject(MimeUtility.encodeText(title));//邮件主题
      emailMessage.setSentDate(new Date());

      emailMessage.setText(content);


      emailMessage.saveChanges();
      Transport transport = session.getTransport("smtp");
      transport.connect(emailHost, emailPort,emailFrom,emailPassword);

      transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
      transport.close();
      log.info("邮件发送成功！Received email address: {}, Title: {}, Content: {}", address, title, content);
    } catch (AddressException e) {
      log.error("邮件发送失败，错误原因："+e.getMessage());
      log.error("请检查邮箱地址:"+e.getMessage());
      throw e;
    } catch (UnsupportedEncodingException e) {
      log.error("邮件发送失败，错误原因：{}",e.getMessage());
      log.error("转码异常，请查看详细信息",e);
      throw e;
    } catch (NoSuchProviderException e) {
      log.error("邮件发送失败，错误原因：{}",e.getMessage());
      log.error("邮件发送失败，详细错误原因：",e);
      throw e;
    }catch (MessagingException e) {
      log.error("邮件发送失败，错误原因{}",e.getMessage());
      log.error("邮件发送失败，有可能的问题为服务器url不存在或附件不存在，具体请看详细信息：",e);
      throw e;
    }
  }
}
