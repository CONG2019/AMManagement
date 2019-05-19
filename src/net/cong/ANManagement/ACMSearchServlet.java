package net.cong.ANManagement;

import net.sf.json.JSONObject;

import javax.naming.directory.SearchResult;
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

@WebServlet(name = "ACMSearchServlet")
public class ACMSearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应内容类型
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        try (PrintWriter out = response.getWriter()) {

            //获得请求中传来的搜索条件，并去除前导和尾随空格
            Map<String, String> SearchCriteria = new HashMap<>();
            String Token = request.getParameter("Token").trim();
            if (request.getParameter("MachineryType") != null){
                SearchCriteria.put("MachineryType", request.getParameter("MachineryType").trim());
            }
            if (request.getParameter("Manufacturer") != null){
                SearchCriteria.put("Manufacturer", request.getParameter("Manufacturer").trim());
            }
            if (request.getParameter("SerialNumber") != null){
                SearchCriteria.put("SerialNumber", request.getParameter("SerialNumber").trim());
            }
            if (request.getParameter("MajorAreas") != null){
                SearchCriteria.put("MajorAreas", request.getParameter("MajorAreas").trim());
            }
            if (request.getParameter("BuyerName") != null){
                SearchCriteria.put("BuyerName", request.getParameter("BuyerName").trim());
            }
            if (request.getParameter("BuyerPhone") != null){
                SearchCriteria.put("BuyerPhone", request.getParameter("BuyerPhone").trim());
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
            //为了简化，只有在登录页才会刷新Token的值

            ArrayList<ACMachinery> SearchResultt = new ArrayList<>();
            SearchResultt = ACMachineryDAO.SelectACMachineries(SearchCriteria);

            //返回搜索结果
            if (SearchResultt == null){
                params.put("SearchResult", "fail");
                jsonObject.put("params", params);
                out.write(jsonObject.toString());
            }else {
                JSONRespon.Respon(SearchResultt, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
