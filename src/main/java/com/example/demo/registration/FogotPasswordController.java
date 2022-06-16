/**
 * @author - Chamath_Wijayarathna
 * Date :6/16/2022
 */


package com.example.demo.registration;

import com.example.demo.appuser.AppUser;
import com.example.demo.appuser.AppUserService;
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
public class FogotPasswordController {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/forgot_password")
    public void showForgotPasswordForm() {
    }

    //
    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model, @RequestBody AppUser appUser) {
        String email = appUser.getEmail();
        String token = RandomString.make(100);
        String resetPasswordLink = null;
        try {
            appUserService.updateResetPasswordToken(token, email);
            resetPasswordLink = Utility.getSiteURL(request) + "/api/v1/registration/reset_password/reset?token=" + token;
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }
        return resetPasswordLink;
    }


    @GetMapping(path = "/reset_password/reset")
    public void showResetPasswordForm(HttpServletRequest request, @RequestParam("token") String token, Model model) {
        AppUser user = appUserService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
        }
    }

    @PostMapping("reset_password/reset")
    public void processResetPassword(HttpServletRequest request, Model model) {
         String token = request.getParameter("token");
        String password = request.getParameter("password");

        AppUser user = appUserService.getByResetPasswordToken(token);
        System.out.println(user.getFirstName());
        model.addAttribute("title", "Reset your password");

        if (user == null) {
            model.addAttribute("message", "Invalid Token");

        } else {
            appUserService.updatePassword(user, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }
    }


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
