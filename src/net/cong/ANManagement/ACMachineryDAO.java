package net.cong.ANManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ACMachineryDAO {
    //这个类是用来对数据库的农机信息表进行增删改查用的类

    /*
    插入新的农机或更新农机信息
    @acMachinery 给定的农机
    @return 返回是否跟新成功
     */
    public static Boolean UpdataACMachinery(ACMachinery acMachinery){
        //标记农机是否存在
        int flag = 0;
        //保存更新的行数
        int result;
        //获得数据库的连接对象
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        //生成SQL代码
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("SELECT * FROM acmachinery WHERE SerialNumber=?");

        //设置数据库的字段值
        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, acMachinery.getSerialNumber());

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                //如果农机存在
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
            //如果农机已经存在，则更新数据
            //获得数据库的连接对象
            connection = DBManager.getConnection();
            preparedStatement = null;
            resultSet = null;
            sqlStatement = new StringBuilder();
            sqlStatement.append("REPLACE INTO acmachinery(MachineryType,Manufacturer,SerialNumber,MajorAreas,BuyerName,BuyerPhone) VALUES(?,?,?,?,?,?)");
            try {
                preparedStatement = connection.prepareStatement(sqlStatement.toString());
                preparedStatement.setString(1, acMachinery.getMachineryType());
                preparedStatement.setString(2, acMachinery.getManufacturer());
                preparedStatement.setString(3, acMachinery.getSerialNumber());
                preparedStatement.setString(4, acMachinery.getMajorAreas());
                preparedStatement.setString(5, acMachinery.getBuyerName());
                preparedStatement.setString(6, acMachinery.getBuyerPhone());

                result = preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("fail");
                return false;
            } finally {
                DBManager.closeAll(connection, preparedStatement, resultSet);
            }
        }else {
            //如果农机已经存在，则更新数据
            //获得数据库的连接对象
            connection = DBManager.getConnection();
            preparedStatement = null;
            resultSet = null;
            sqlStatement = new StringBuilder();
            sqlStatement.append("INSERT INTO acmachinery(MachineryType,Manufacturer,SerialNumber,MajorAreas,BuyerName,BuyerPhone) VALUES(?,?,?,?,?,?)");
            try {
                preparedStatement = connection.prepareStatement(sqlStatement.toString());
                preparedStatement.setString(1, acMachinery.getMachineryType());
                preparedStatement.setString(2, acMachinery.getManufacturer());
                preparedStatement.setString(3, acMachinery.getSerialNumber());
                preparedStatement.setString(4, acMachinery.getMajorAreas());
                preparedStatement.setString(5, acMachinery.getBuyerName());
                preparedStatement.setString(6, acMachinery.getBuyerPhone());

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
   提供一个用任意属性搜索农机的方法

   @param 用户地址
   @return 返回一个符合要求的用户的集合
    */
    public static ArrayList<ACMachinery> SelectACMachineries(Map<String, String> param){
        ArrayList<ACMachinery> Result = new ArrayList<>();

        //获得数据库的连接对象
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        //生成SQL代码
        StringBuilder sqlStatement = new StringBuilder();
        //sqlStatement.append("SELECT * FROM user WHERE UserAddress=?");
        boolean isfirst = true;
        //先判断传入的参数是不是空的
        if(param.size() == 0){
            return null;
        }
        //不为空，继续往下执行
        Set<String> keyset = param.keySet();
        String statement = null;  //用来保存动态生成的sql查询语句
        for (String key: keyset
             ) {
            if(isfirst){
                statement = "SELECT * FROM acmachinery WHERE " + key + "=" + "'" + param.get(key) + "'";
                isfirst = false;
            }else {
                statement = statement + " and " + key + "=" + "'" + param.get(key) + "'";
            }
        }
        //调试用
        System.out.println(statement);
        sqlStatement.append(statement);


        //设置数据库的字段值
        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            //preparedStatement.setString(1, userAdress);

            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                ACMachinery acMachinery = new ACMachinery();
                acMachinery.setMachineryType(resultSet.getString("MachineryType"));
                acMachinery.setManufacturer(resultSet.getString("Manufacturer"));
                acMachinery.setSerialNumber(resultSet.getString("SerialNumber"));
                acMachinery.setMajorAreas(resultSet.getString("MajorAreas"));
                acMachinery.setBuyerName(resultSet.getString("BuyerName"));
                acMachinery.setBuyerPhone(resultSet.getString("BuyerPhone"));
                Result.add(acMachinery);
            }

            if (Result.size() == 0){
                return null;
            }else {
                return Result;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("fail");
            return Result;
        } finally {
            DBManager.closeAll(connection, preparedStatement, resultSet);
        }
    }

}
