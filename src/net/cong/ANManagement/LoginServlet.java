package net.cong.ANManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

/**
 * 测试登录Servlet
 *
 * @author Implementist
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //测试服务器是否可达用
//        processRequest(request, response);

        // 设置响应内容类型
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        //测试用代码
//        User user_ = new User();
//        user_.setUserAddress("广东-广州");
//        user_.setUserPhone("13211063061");
//        user_.setUserName("Jackson");
//        user_.setPassword(enPassword("87654321"));
//        Boolean p = UserDAO.UpdataUser(user_);
//        user_.setUserName("Mike");
//        user_.setUserAddress("广东-广州");
//        user_.setUserPhone("13266663061");
//        p = UserDAO.UpdataUser(user_);


        try (PrintWriter out = response.getWriter()) {

            //获得请求中传来的用户名和密码，并去除前导和尾随空格
            String Token = request.getParameter("Token").trim();
            String accountNumber = request.getParameter("AccountNumber").trim();
            String password = request.getParameter("Password").trim();
            //保存在数据库的密码是用md5加密过的，先计算出加密后的密码
            password = enPassword(password);

            Map<String, String> params = new HashMap<>();
            JSONObject jsonObject = new JSONObject();

            //先判断用户是否已经登录
            if(IsLogin.isLogin(Token, request)){
                //已经登录，返回成功，并退出
                params.put("Result", "success");
                params.put("Token", Token);
                jsonObject.put("params", params);
                out.write(jsonObject.toString());
                return;
            }

            //如果用户的登录过期或还未登录
            //密码验证结果
                User user = verifyLogin(accountNumber, password);
                if (user != null) {
                    params.put("Result", "success");
                    //账号密码正确，保存相关用户信息到session中，username是唯一的，这里用username作为token返回给客户端
                    String token_ = accountNumber;
                    HttpSession session = request.getSession();
                    session.setAttribute(token_, user);
                    params.put("Token", token_);
                } else {
                    params.put("Result", "fail");
                }
                jsonObject.put("params", params);
//                out.write(jsonObject.toString());
//                jsonObject = (JSONObject.fromObject(params));
                out.write(jsonObject.toString());
//                JSONRespon.Respon(jsonObject, response);
//                JSONRespon.Respon(UserDAO.SelectUsers("广东-广州"), response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        PrintWriter out = response.getWriter();
//        out.println("nhao!");
        doPost(request, response);

    }

    /**
     * 验证用户名密码是否正确
     *
     * @param userName
     * @param password
     */
    private User verifyLogin(String userName, String password) {
        User user = UserDAO.queryUser(userName);

        //账户密码验证
//        return null != user && password.equals(user.getPassword());
        if(user != null && password.equals(user.getPassword())){
            return user;
        }else{
            return null;
        }
    }

    //md5加密算法
    // 进行md5的加密运算
    public static String md5(String password) {
        // MessageDigest专门用于加密的类
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] result = messageDigest.digest(password.getBytes()); // 得到加密后的字符组数

            StringBuffer sb = new StringBuffer();

            for (byte b : result) {
                int num = b & 0xff; // 这里的是为了将原本是byte型的数向上提升为int型，从而使得原本的负数转为了正数
                String hex = Integer.toHexString(num); //这里将int型的数直接转换成16进制表示
                //16进制可能是为1的长度，这种情况下，需要在前面补0，
                if (hex.length() == 1) {
                    sb.append(0);
                }
                sb.append(hex);
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    //用多重md5对密码进行加密
    public static String enPassword(String password){
        return md5(password + md5(password));
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

}
