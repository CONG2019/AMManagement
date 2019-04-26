package net.cong.ANManagement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class IsLogin {
    public static Boolean isLogin(String token, HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session.getAttribute(token) != null){
            return true;
        }else {
            return false;
        }
    }
}
