/**
 * @author - Chamath_Wijayarathna
 * Date :6/16/2022
 */


package com.example.demo.utility;

import javax.servlet.http.HttpServletRequest;


public class Utility {

    // Get Get Site URL To send Mail For Reset Password Mail
    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

}
