/**
 * @author - Chamath_Wijayarathna
 * Date :6/16/2022
 */

package com.example.demo.business.impl;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Service
public class RealEmailBOImpl { // Send Real Email To User

    public void sendMailToNewAppUser(String name, String email) {
        String smsSender = "chamathrivindu12000@gmail.com";


        String to = email;

        Properties props = new Properties();

        props.put("mail.smtp.host", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        props.put("mail.smtp.starttls.enable", "true");

        props.put("mail.smtp.host", "smtp.gmail.com");

        props.put("mail.smtp.port", "587");

        props.put("mail.smtp.auth", "true");

        //Establishing a session with required user details

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(smsSender, "oighhaziivrlroax");

            }

        });

        try {

            //Creating a Message object to set the email content

            MimeMessage msg = new MimeMessage(session);
            InternetAddress[] address = InternetAddress.parse(to, true);
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(" App User Registration");
            msg.setSentDate(new Date());
            msg.setFrom(smsSender);
            msg.setContent(
                    "<!DOCTYPE html>\n" +
                            "<html lang=\"en\">\n" +
                            "<head>\n" +
                            "    <meta charset=\"UTF-8\">\n" +
                            "    <title>Title</title>\n" +
                            "</head>\n" +
                            "<!-- Complete Email template -->\n" +
                            "<body style=\"background-color:grey\">\",\" <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"550\" bgcolor=\"white\" style=\"border:2px solid black\"><tbody>\n" +
                            "    <tr>\n" +
                            "        <td align=\"center\">\n" +
                            "            <table align=\"center\" border=\"0\" cellpadding=\"0\"\n" +
                            "                   cellspacing=\"0\" class=\"col-550\" width=\"550\">\n" +
                            "                <tbody>\n" +
                            "                <tr>\n" +
                            "                    <td align=\"center\" style=\"background-color: #4cb96b;\n" +
                            "\t\t\t\t\t\t\t\t\t\theight: 50px;\">\n" +
                            "\n" +
                            "                        <a href=\"#\" style=\"text-decoration: none;\">\n" +
                            "                            <p style=\"color:white;\n" +
                            "\t\t\t\t\t\t\t\t\t\t\t\tfont-weight:bold;\">\n" +
                            "                                Cloud Of Goods\n" +
                            "                            </p>\n" +
                            "                        </a>\n" +
                            "                    </td>\n" +
                            "                </tr>\n" +
                            "                </tbody>\n" +
                            "            </table>\n" +
                            "        </td>\n" +
                            "    </tr>\n" +
                            "    <tr style=\"height: 300px;\">\n" +
                            "        <td align=\"center\" style=\"border: none;\n" +
                            "\t\t\t\t\t\tborder-bottom: 2px solid #4cb96b;\n" +
                            "\t\t\t\t\t\tpadding-right: 20px;padding-left:20px\">\n" +
                            "\n" +
                            "            <p style=\"font-weight: bolder;font-size: 42px;\n" +
                            "\t\t\t\t\t\t\tletter-spacing: 0.025em;\n" +
                            "\t\t\t\t\t\t\tcolor:black;\">\n" +
                            "                Hello Cloud Of Goods User " + name + " !\n" +
                            "                <br><h3> Your Email " + to + " Successfully Registered to the System</h3>\n" +
                            "            </p>\n" +
                            "        </td>\n" +
                            "    </tr>\n" +
                            "\n" +
                            "    <tr style=\"display: inline-block;\">\n" +
                            "        <td style=\"height: 150px;\n" +
                            "\t\t\t\t\t\tpadding: 20px;\n" +
                            "\t\t\t\t\t\tborder: none;\n" +
                            "\t\t\t\t\t\tborder-bottom: 2px solid #361B0E;\n" +
                            "\t\t\t\t\t\tbackground-color: white;\">\n" +
                            "\n" +
                            "            <h2 style=\"text-align: left;\n" +
                            "\t\t\t\t\t\t\talign-items: center;\">\n" +
                            "                Thanks For registering : LoremLoremLoremLoremLorem\n" +
                            "            </h2>\n" +
                            "            <p class=\"data\"\n" +
                            "               style=\"text-align: justify-all;\n" +
                            "\t\t\t\t\t\t\talign-items: center;\n" +
                            "\t\t\t\t\t\t\tfont-size: 15px;\n" +
                            "\t\t\t\t\t\t\tpadding-bottom: 12px;\">\n" +
                            "            Lorem\n" +
                            "            </p>\n" +
                            "            <p>\n" +
                            "                <a href=\n" +
                            "                           \"\"\n" +
                            "                   style=\"text-decoration: none;\n" +
                            "\t\t\t\t\t\t\t\tcolor:black;\n" +
                            "\t\t\t\t\t\t\t\tborder: 2px solid #4cb96b;\n" +
                            "\t\t\t\t\t\t\t\tpadding: 10px 30px;\n" +
                            "\t\t\t\t\t\t\t\tfont-weight: bold;\">\n" +
                            "                    Read More\n" +
                            "                </a>\n" +
                            "            </p>\n" +
                            "        </td>\n" +
                            "    </tr>\n" +
                            "    </tbody>\n" +
                            "</table>\n" +
                            "</body>\n" +
                            "\n" +
                            "</html>"
                    , "text/html"
            );
            msg.setHeader("XPriority", "1");
            Transport.send(msg);
            System.out.println("Mail has been sent successfully");

        } catch (MessagingException mex) {

            System.out.println("Unable to send an email" + mex);


        }

    }
}
