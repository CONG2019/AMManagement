package net.cong.ANManagement;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JSONRespon {

    //用Json格式数据和客户端通行
    public static Boolean Respon(JSONObject jsonObject, HttpServletResponse response){
        JSONObject jsonObject_ = new JSONObject();
        Map<String, JSONObject> params = new HashMap<>();
        params.put("JSONObject", jsonObject);
        jsonObject_.put("params", params);
        try (PrintWriter out = response.getWriter()){
            out.write(jsonObject_.toString());
            return true;
        }catch (IOException e){
            Logger.getLogger(JSONRespon.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    //给客户端返回一个用户信息
    public static Boolean Respon(User user, HttpServletResponse response){
        Map<String, JSONObject> params = new HashMap<>();
        JSONObject jsonObject_ = new JSONObject();

        jsonObject_.put("UserName", user.getUserName());
//        params.put("Password", user.getPassword());
        jsonObject_.put("UserPhone", user.getUserPhone());
        jsonObject_.put("UserAddress", user.getUserAddress());

        params.put("User", jsonObject_);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("params", params);

        try (PrintWriter out = response.getWriter()){
            out.write(jsonObject.toString());
            return true;
        }catch (IOException e){
            Logger.getLogger(JSONRespon.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    //给客户端返回一组用户信息
    public static Boolean Respon(ArrayList<User> users, HttpServletResponse response){
        Map<String, JSONArray> params = new HashMap<>();
        JSONArray jsonArray = new JSONArray();

        for (User user: users
             ) {
            JSONObject jsonObject = new JSONObject();
            //将用户信息保存到一个jsonObject
            jsonObject.put("UserName", user.getUserName());
//            jsonObject.put("Password", user.getPassword());
            jsonObject.put("UserPhone", user.getUserPhone());
            jsonObject.put("UserAddress", user.getUserAddress());
            //将用户对应的jsonObject放到jsonArray中
            jsonArray.add(jsonObject);
        }

        JSONObject responUsers = new JSONObject();
        //将格式转化好的jsonArray放到params，且索引值为users
        params.put("JSONArray", jsonArray);
        //将params放到responUsers中，客户端按照这样的相反顺序就可以读取到users的信息
        responUsers.put("params", params);

        try (PrintWriter out = response.getWriter()){
            out.write(responUsers.toString());
            return true;
        }catch (IOException e){
            Logger.getLogger(JSONRespon.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
}
