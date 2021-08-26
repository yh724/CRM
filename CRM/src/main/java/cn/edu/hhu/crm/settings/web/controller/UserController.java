package cn.edu.hhu.crm.settings.web.controller;

import cn.edu.hhu.crm.settings.domain.User;
import cn.edu.hhu.crm.settings.service.UserService;
import cn.edu.hhu.crm.settings.service.impl.UserServiceImpl;
import cn.edu.hhu.crm.utils.MD5Util;
import cn.edu.hhu.crm.utils.PrintJson;
import cn.edu.hhu.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到控制器UserController");
        String path = request.getServletPath();
        if("/settings/user/login.do".equals(path)){
            login(request,response);
        }
    }

    private void login(HttpServletRequest request,HttpServletResponse response){
        String loginAct = request.getParameter("loginAct");
        String password = request.getParameter("loginPwd");
        String loginPwd = MD5Util.getMD5(password);
        String loginIp = request.getRemoteAddr();
        System.out.println(loginIp);
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        try{
            User user = us.login(loginAct,loginPwd,loginIp);
            request.getSession().setAttribute("user",user);
            PrintJson.printJsonFlag(response,true);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,Object> map = new HashMap<>();
            map.put("success",false);
            map.put("msg",e.getMessage());
            PrintJson.printJsonObj(response,map);
        }

    }
}
