package net.cong.ANManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class UserDAO {
    /**
     * 查询给定用户名的用户的详细信息
     *
     * @param userName 给定的用户名
     * @return 查询到的封装了详细信息的User对象
     */
    public static User queryUser(String userName) {
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
            User user = new User();
            if (resultSet.next()) {
                user.setUserName(resultSet.getString("UserName"));
                user.setPassword(resultSet.getString("Password"));
                user.setUserPhone(resultSet.getString("UserPhone"));
                user.setUserAddress(resultSet.getString("UserAddress"));
                return user;
            } else {
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

    /*
    插入新的用户或更新用户信息

    @param 给定的用户
    @return 返回是否跟新成功
     */
    public static Boolean UpdataUser(User user){
        //标记用户是否存在
        int flag = 0;
        //保存更新的行数
        int result;
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
            preparedStatement.setString(1, user.getUserName());

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                //如果用户存在
                flag = 1;
            } else {
                flag = 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("fail");
            return false;
        } finally {
            DBManager.closeAll(connection, preparedStatement, resultSet);
        }

        if(flag == 1){
            //如果用户已经存在，则更新数据
            //获得数据库的连接对象
            connection = DBManager.getConnection();
            preparedStatement = null;
            resultSet = null;
            sqlStatement = new StringBuilder();
            sqlStatement.append("REPLACE INTO user(UserName,Password,UserPhone,UserAddress,Token) VALUES(?,?,?,?,?)");
            try {
                preparedStatement = connection.prepareStatement(sqlStatement.toString());
                preparedStatement.setString(1, user.getUserName());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getUserPhone());
                preparedStatement.setString(4, user.getUserAddress());
                preparedStatement.setString(5, user.getToken());

                result = preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("fail");
                return false;
            } finally {
                DBManager.closeAll(connection, preparedStatement, resultSet);
            }
        }else {
            //如果用户已经存在，则更新数据
            //获得数据库的连接对象
            connection = DBManager.getConnection();
            preparedStatement = null;
            resultSet = null;
            sqlStatement = new StringBuilder();
            sqlStatement.append("INSERT INTO user(UserName,Password,UserPhone,UserAddress,Token) VALUES(?,?,?,?,?)");
            try {
                preparedStatement = connection.prepareStatement(sqlStatement.toString());
                preparedStatement.setString(1, user.getUserName());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getUserPhone());
                preparedStatement.setString(4, user.getUserAddress());
                preparedStatement.setString(5, user.getToken());

                result = preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("fail");
                return false;
            } finally {
                DBManager.closeAll(connection, preparedStatement, resultSet);
            }
        }

        if(result > 0){
            return true;
        }else {
            return false;
        }
    }

    /*
    提供一个用地址搜索用户的方法

    @param 用户地址
    @return 返回一个符合要求的用户的集合
     */
    public static ArrayList<User> SelectUsers(String userAdress){
        ArrayList<User> Result = new ArrayList<>();

        //获得数据库的连接对象
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        //生成SQL代码
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("SELECT * FROM user WHERE UserAddress=?");

        //设置数据库的字段值
        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, userAdress);

            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                User user = new User();
                user.setUserName(resultSet.getString("UserName"));
                user.setPassword(resultSet.getString("Password"));
                user.setUserPhone(resultSet.getString("UserPhone"));
                user.setUserAddress(resultSet.getString("UserAddress"));
                Result.add(user);
            }

            return Result;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("fail");
            return Result;
        } finally {
            DBManager.closeAll(connection, preparedStatement, resultSet);
        }
    }

}
