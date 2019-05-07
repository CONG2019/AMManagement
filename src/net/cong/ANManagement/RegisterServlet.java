package net.cong.ANManagement;

import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应内容类型
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        try (PrintWriter out = response.getWriter()) {

            //获得请求中传来的用户名和密码，并去除前导和尾随空格
            String UserName = request.getParameter("UserName").trim();
            String UserAddress = request.getParameter("UserAddress").trim();
            String Password = request.getParameter("Password").trim();
            String PhoneNumber = request.getParameter("PhoneNumber").trim();
            //保存在数据库的密码是用md5加密过的，先计算出加密后的密码
            Password = enPassword(Password);

            System.out.println(UserAddress);

            User user = new User();
            user.setUserAddress(UserAddress);
            user.setUserName(UserName);
            user.setPassword(Password);
            user.setUserPhone(PhoneNumber);
            user.setToken("");

            System.out.println("UserAddress: " + user.getUserAddress());

            Map<String, String> params = new HashMap<>();
            JSONObject jsonObject = new JSONObject();

            if (UserDAO.queryUser(user.getUserName()) != null){
                jsonObject.put("RegisterResult", "username already exists");
                JSONRespon.Respon(jsonObject, response);
                return;
            }

            user.setToken(user.getUserName() + "," + new Date().getTime());

            if(UserDAO.UpdataUser(user)){
                jsonObject.put("RegisterResult", "success");
                JSONRespon.Respon(jsonObject, response);
                return;
            }else {
                jsonObject.put("RegisterResult", "fail");
                JSONRespon.Respon(jsonObject, response);
                return;
            }


        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
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
}
