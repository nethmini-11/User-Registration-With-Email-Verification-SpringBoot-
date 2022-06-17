/**
 * @author - Chamath_Wijayarathna
 * Date :6/16/2022
 */


package com.example.demo.controller;

import com.example.demo.business.AppUserServiceBO;
import com.example.demo.entity.AppUser;
import com.example.demo.utility.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(path = "api/v1/registration")
public class ForgotPasswordController {
    @Autowired
    // Extended MailSender interface for JavaMail, supporting MIME messages -
    // both as direct arguments and through preparation callbacks.
    private JavaMailSender mailSender;

    @Autowired
    private AppUserServiceBO appUserServiceBO;

    @GetMapping("/forgot_password") // Load Forgot Password Page
    public void showForgotPasswordForm() {
    }

    //
    @PostMapping("/forgot_password") // User Send Forgot Password Email in Post
    public String processForgotPassword(HttpServletRequest request, Model model, @RequestBody AppUser appUser) {
        String email = appUser.getEmail();
        String token = RandomString.make(200); // Generate Random Token Length 200 Characters
        String resetPasswordLink = null;
        try {
            // Update Reset Password Method check User Is Enabled (Registration Confirmed By Email)
            appUserServiceBO.updateResetPasswordToken(token, email);
            // Create Reset Password Link With Token
            resetPasswordLink = Utility.getSiteURL(request) + "/api/v1/registration/reset_password/reset?token=" + token;
            sendEmail(email, resetPasswordLink); // Calling to Send Email method to send email
            // Pass Message to Interface
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            // UnsupportedEncodingException can only be thrown if I specify a wrong encoding
            model.addAttribute("error", "Error while sending email");
        }
        return resetPasswordLink; // Return Link For Dev Purpose
    }// End Method


    @GetMapping(path = "/reset_password/reset")
    // Method Working Once Click the Email Change my password Link
    public void showResetPasswordForm(@RequestParam("token") String token, Model model) {
        // Database Check Is there any User In the database from this token
        AppUser user = appUserServiceBO.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
        }
    }

    @PostMapping("reset_password/reset") // Method to Change Password
    public void processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        AppUser user = appUserServiceBO.getByResetPasswordToken(token);// Get Password From Token
        model.addAttribute("title", "Reset your password");

        if (user == null) {
            model.addAttribute("message", "Invalid Token");

        } else {
            appUserServiceBO.updatePassword(user, password); // Reset Password

            model.addAttribute("message", "You have successfully changed your password.");
        }
    }


    // Email Send Method
    public void sendEmail(String recipientEmail, String resetPasswordLinkAndToken) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("chamathrivindu12000@gmail.com", "Rivindu");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" + "\n" + "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" + "\n" + "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" + "    <tbody><tr>\n" + "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" + "        \n" + "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" + "          <tbody><tr>\n" + "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" + "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" + "                  <tbody><tr>\n" + "                    <td style=\"padding-left:10px\">\n" + "                  \n" + "                    </td>\n" + "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" + "                      <span style=\"margin-top:100px,font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Reset your Password</span>\n" + "                    </td>\n" + "                  </tr>\n" + "                </tbody></table>\n" + "              </a>\n" + "            </td>\n" + "          </tr>\n" + "        </tbody></table>\n" + "        \n" + "      </td>\n" + "    </tr>\n" + "  </tbody></table>\n" + "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" + "    <tbody><tr>\n" + "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" + "      <td>\n" + "        \n" + "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" + "                  <tbody><tr>\n" + "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" + "                  </tr>\n" + "                </tbody></table>\n" + "        \n" + "      </td>\n" + "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" + "    </tr>\n" + "  </tbody></table>\n" + "\n" + "\n" + "\n" + "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" + "    <tbody><tr>\n" + "      <td height=\"30\"><br></td>\n" + "    </tr>\n" + "    <tr>\n" + "      <td width=\"10\" valign=\"middle\"><br></td>\n" + "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" + "        \n" + "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Have a nice day. <br> You have requested to reset your password. Please click on the below link to Reset Your Password: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + resetPasswordLinkAndToken + "\">Change my password</a> </p></blockquote>\n Ignore this email if you do remember your password, \"\n" + "            + \"or you have not made the request. <p>See you soon</p>" + "        \n" + "      </td>\n" + "      <td width=\"10\" valign=\"middle\"><br></td>\n" + "    </tr>\n" + "    <tr>\n" + "      <td height=\"30\"><br></td>\n" + "    </tr>\n" + "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" + "\n" + "</div></div>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

}
