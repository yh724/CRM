package cn.edu.hhu.crm.settings.web.controller;

import cn.edu.hhu.crm.settings.domain.User;
import cn.edu.hhu.crm.settings.service.UserService;
import cn.edu.hhu.crm.settings.service.impl.UserServiceImpl;
import cn.edu.hhu.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Provider;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if("/settings/user/login.do".equals(path)){
            boolean flag = false;
            flag = login(request);
            if(flag)
                System.out.println("登录成功！");
            else
                System.out.println("登录失败！");
        }else if("/settings/user/xx.do".equals(path)){
            System.out.println("error!");
        }
    }
    public static boolean login(HttpServletRequest request){
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        String loginAct = request.getParameter("loginAct");
        String password = request.getParameter("loginPwd");
        Map<String,String> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("password",password);
        User user = us.select01(map);
        if(user != null){
            System.out.println(user);
            return true;
        }


        return false;
    }
}
