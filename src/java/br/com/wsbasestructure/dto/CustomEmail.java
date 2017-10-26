package br.com.wsbasestructure.dto;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Andrew Ribeiro
 */
public class CustomEmail {

    private List<String> recipients;
    private String body;
    private String subject;
    private String sender;
    private String from;
    private String senderName;
    private String fromName;
    private Authenticator auth;
    private Properties properties;
    private Session session;

    public CustomEmail(String protocol, String host, String port, Authenticator auth, String sender) {
        properties = new Properties();
        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.port", port);
        this.auth = auth;
        this.sender = sender;
    }

    public void send() throws MessagingException, UnsupportedEncodingException {

        session = Session.getInstance(properties, auth);

        MimeMessage m = new MimeMessage(session);
        m.setSender(new InternetAddress(sender, senderName, "utf-8"));
        m.setFrom(m.getSender());
        for (String recipient : recipients) {
            m.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        }
        m.setSubject(subject);
        m.setText(body, "utf-8", "html");
        
        Transport.send(m);
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Authenticator getAuth() {
        return auth;
    }

    public void setAuth(Authenticator auth) {
        this.auth = auth;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

}