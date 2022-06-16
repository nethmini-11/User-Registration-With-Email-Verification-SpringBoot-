/**
 * @author - Chamath_Wijayarathna
 * Date :6/16/2022
 */


package com.example.demo.utility;

import javax.servlet.http.HttpServletRequest;

public class Utility {

        public static String getSiteURL(HttpServletRequest request) {
            String siteURL = request.getRequestURL().toString();
            return siteURL.replace(request.getServletPath(), "");
        }

}
