package net.cong.ANManagement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IsLogin {
    //判断是否登陆了
    public static Boolean isLogin(String token){
//        HttpSession session = request.getSession();
        String[] token_ = token.split(",");
        String Sever_token = GetToken(token_[0]);
        if (Sever_token != null && Sever_token.equals(token)){
            Date before_ = new Date(Long.parseLong(token_[1]));
            Date now_ = new Date();
            //判断是否超过7天
            if(now_.getTime() - before_.getTime() < 60480000L){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    public static String GetToken(String userName){
        //获得数据库的连接对象
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        //生成SQL代码
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("SELECT * FROM user WHERE UserName=?");

        //设置数据库的字段值
        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, userName);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println(resultSet.getString("Token"));
                return resultSet.getString("Token");
            } else {
                System.out.println("error");
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("fail");
            return null;
        } finally {
            DBManager.closeAll(connection, preparedStatement, resultSet);
        }
    }

    public static Boolean SaveToken(String token){
        //获得数据库的连接对象
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String[] token_ = token.split(",");

        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("UPDATE user set Token=? where UserName=?");
        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1,token);
            preparedStatement.setString(2,token_[0]);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("fail");
            return false;
        } finally {
            DBManager.closeAll(connection, preparedStatement, resultSet);
        }
    }
}
