package net.cong.ANManagement;

import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ACMIInputServlet")
public class ACMIInputServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应内容类型
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        try (PrintWriter out = response.getWriter()) {

            //获得请求中农机信息，并去除前导和尾随空格
            String Token = request.getParameter("Token").trim();
            ACMachinery acMachinery = new ACMachinery();
            if (request.getParameter("MachineryType") != null){
                acMachinery.setMachineryType(request.getParameter("MachineryType").trim());
            }
            if (request.getParameter("Manufacturer") != null){
                acMachinery.setManufacturer(request.getParameter("Manufacturer").trim());
            }
            if (request.getParameter("SerialNumber") != null){
                acMachinery.setSerialNumber(request.getParameter("SerialNumber").trim());
            }
            if (request.getParameter("MajorAreas") != null){
                acMachinery.setMajorAreas(request.getParameter("MajorAreas").trim());
            }
            if (request.getParameter("BuyerName") != null){
                acMachinery.setBuyerName(request.getParameter("BuyerName").trim());
            }
            if (request.getParameter("BuyerPhone") != null){
                acMachinery.setBuyerPhone(request.getParameter("BuyerPhone").trim());
            }
            Map<String, String> params = new HashMap<>();
            JSONObject jsonObject = new JSONObject();

            //先判断用户是否已经登录
//            System.out.println("TOKEN: " + Token);
            if(!IsLogin.isLogin(Token)){
                //未登录，退出
                params.put("Result", "fail");
                jsonObject.put("params", params);
                out.write(jsonObject.toString());
                return;
            }
            //为了简化，只有在登录页才会刷新Token的

            if (ACMachineryDAO.UpdataACMachinery(acMachinery)){
                jsonObject.put("UpdataResult", "success");
                JSONRespon.Respon(jsonObject, response);
                return;
            }else {
                jsonObject.put("UpdataResult", "fail");
                JSONRespon.Respon(jsonObject, response);
                return;
            }


        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
