package com.demo.demo.model.service;

import java.util.logging.Logger;

import com.demo.demo.model.Client;
import com.demo.demo.model.Ticket;
import com.demo.demo.model.TicketStatus;
import com.demo.demo.model.repo.ClientRepository;
import com.demo.demo.model.repo.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;

import java.io.IOException;
import java.util.Properties;

@Service
public class EmailProcessorService {

    private static final Logger logger = Logger.getLogger(EmailProcessorService.class.getName());

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public void processEmails() {
        try {
            Properties properties = new Properties();
            properties.setProperty("mail.store.protocol", "pop3s");
            properties.setProperty("mail.pop3s.host", "pop.gmail.com");
            properties.setProperty("mail.pop3s.port", "995");
            properties.setProperty("mail.pop3s.auth", "true");
            properties.setProperty("mail.pop3s.ssl.trust", "*");

            Session session = Session.getDefaultInstance(properties, null);
            Store store = session.getStore("pop3s");
            store.connect("mangupawar830@gmail.com", "jgqd tzjo ogoj lscc");

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);
            Message[] messages = inbox.getMessages();

            for (Message message : messages) {
                try {
                    if (!message.isSet(Flags.Flag.SEEN)) {
                        Address[] froms = message.getFrom();
                        String email = froms == null ? null : ((InternetAddress) froms[0]).getAddress();

                        logger.info("Received email from: " + email);

                        String emailSubject = message.getSubject();
                        String emailBody = extractEmailBody(message);

                        
                        if ("mangupawar143@gmail.com".equals(email)) {
                            logger.info("Creating ticket for client: " + email);
                            createTicket(email, emailSubject, emailBody);
                            message.setFlag(Flags.Flag.SEEN, true);
                        }
                    }
                } catch (Exception e) {
                    logger.severe("Error processing email: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            if (inbox.isOpen()) {
                inbox.close(true);
            }
            store.close();
        } catch (Exception e) {
            logger.severe("Error processing emails: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createTicket(String clientEmail, String emailSubject, String emailBody) {
        Client client = getClientByEmail(clientEmail);
        if (client != null) {
            logger.info("Client found: " + client);
            Ticket newTicket = new Ticket();
            newTicket.setClient(client);
            newTicket.setStatus(TicketStatus.OPEN);
            newTicket.setClient(client);
            newTicket.setTitle(emailSubject);
            String truncatedText = truncateText(emailBody, 255); 
            newTicket.setText(truncatedText);
            ticketRepository.save(newTicket);
            logger.info("Ticket created for client: " + clientEmail);
        } else {
            logger.warning("Client not found for email: " + clientEmail);
        }
    }
    
    private String truncateText(String text, int maxLength) {
        return text.length() <= maxLength ? text : text.substring(0, maxLength);
    }

    private Client getClientByEmail(String clientEmail) {
        return clientRepository.findBySupportEmail(clientEmail);
    }

    private String extractEmailBody(Message message) {
        try {
            Object content = message.getContent();

            if (content instanceof MimeMultipart) {
                MimeMultipart mimeMultipart = (MimeMultipart) content;
                StringBuilder body = new StringBuilder();

                for (int i = 0; i < mimeMultipart.getCount(); i++) {
                    BodyPart bodyPart = mimeMultipart.getBodyPart(i);
                    String partContent = bodyPart.getContent().toString();
                    body.append(partContent);
                }

                return body.toString();
            } else if (content != null) {
                return content.toString();
            } else {
                return "";
            }
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
            return "Error extracting email body";
        }
    }


}
