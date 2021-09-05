package cn.edu.hhu.crm.workbench.web.controller;

import cn.edu.hhu.crm.settings.domain.User;
import cn.edu.hhu.crm.settings.service.UserService;
import cn.edu.hhu.crm.settings.service.impl.UserServiceImpl;
import cn.edu.hhu.crm.utils.DateTimeUtil;
import cn.edu.hhu.crm.utils.PrintJson;
import cn.edu.hhu.crm.utils.ServiceFactory;
import cn.edu.hhu.crm.utils.UUIDUtil;
import cn.edu.hhu.crm.workbench.domain.Customer;
import cn.edu.hhu.crm.workbench.domain.Tran;
import cn.edu.hhu.crm.workbench.domain.TranHistory;
import cn.edu.hhu.crm.workbench.service.TransService;
import cn.edu.hhu.crm.workbench.service.impl.TransServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransController extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/workbench/trans/add.do".equals(path)) {
            add(request,response);
        }else if("/workbench/trans/getCustomerName.do".equals(path)){
            getCustomerName(request,response);
        }else if("/workbench/trans/save.do".equals(path)){
            save(request,response);
        }else if("/workbench/trans/detail.do".equals(path)){
            detail(request,response);
        }else if("/workbench/trans/getHistroyList.do".equals(path)){
            getHistroyList(request,response);
        }else if("/workbench/trans/changeStage.do".equals(path)){
            changeStage(request,response);
        }else if("/workbench/trans/getCharts.do".equals(path)){
            getCharts(request,response);
        }
    }

    private void getCharts(HttpServletRequest request, HttpServletResponse response) {
        TransService ts = (TransService) ServiceFactory.getService(new TransServiceImpl());
        Map<String,Object> mapList = ts.getCharts();
        PrintJson.printJsonObj(response,mapList);
    }

    private void changeStage(HttpServletRequest request, HttpServletResponse response) {
        String tranId = request.getParameter("id");
        String id = UUIDUtil.getUUID();
        String money = request.getParameter("money");
        String stage = request.getParameter("stage");
        String expectedDate = request.getParameter("expectedDate");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();

        Tran t = new Tran();
        t.setId(tranId);
        t.setStage(stage);
        t.setEditBy(createBy);
        t.setEditTime(createTime);

        TranHistory th = new TranHistory();
        th.setId(id);
        th.setMoney(money);
        th.setTranId(tranId);
        th.setStage(stage);
        th.setCreateBy(createBy);
        th.setCreateTime(createTime);
        th.setExpectedDate(expectedDate);

        TransService ts = (TransService) ServiceFactory.getService(new TransServiceImpl());
        boolean flag = ts.changeStage(t,th);
        Map<String,Object> map = new HashMap<>();
        //data:  {"success":true/false,"editBy":"xxx","editTime":"xxx"}
        map.put("success",flag);
        map.put("editBy",createBy);
        map.put("editTime",createTime);
        PrintJson.printJsonObj(response,map);

    }

    private void getHistroyList(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        TransService ts = (TransService) ServiceFactory.getService(new TransServiceImpl());
        List<TranHistory> thList = ts.getHistroyList(id);
        PrintJson.printJsonObj(response,thList);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        TransService ts = (TransService) ServiceFactory.getService(new TransServiceImpl());
        Tran t = ts.getInfoById(id);
        request.setAttribute("t",t);
        request.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(request,response);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = UUIDUtil.getUUID();
        String owner =request.getParameter("owner");
        String money =request.getParameter("money");
        String name =request.getParameter("name");
        String expectedDate =request.getParameter("expectedDate");
        String customerName =request.getParameter("customerName");
        String stage =request.getParameter("stage");
        String type =request.getParameter("type");
        String source =request.getParameter("source");
        String activityId =request.getParameter("activityId");
        String contactsId =request.getParameter("contactsId");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String description =request.getParameter("description");
        String contactSummary =request.getParameter("contactSummary");
        String nextContactTime =request.getParameter("nextContactTime");

        Tran t = new Tran();
        t.setId(id);
        t.setMoney(money);
        t.setOwner(owner);
        t.setName(name);
        t.setExpectedDate(expectedDate);
        t.setStage(stage);
        t.setType(type);
        t.setSource(source);
        t.setActivityId(activityId);
        t.setContactsId(contactsId);
        t.setCreateTime(createTime);
        t.setCreateBy(createBy);
        t.setDescription(description);
        t.setContactSummary(contactSummary);
        t.setNextContactTime(nextContactTime);

        TransService ts = (TransService) ServiceFactory.getService(new TransServiceImpl());
        boolean flag = ts.save(t,customerName);
        if(flag){
            response.sendRedirect(request.getContextPath()+"/workbench/transaction/index.jsp");
        }

    }

    private void getCustomerName(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        TransService ts = (TransService) ServiceFactory.getService(new TransServiceImpl());
        List<String> customerName = ts.getCustomerName(name);
        PrintJson.printJsonObj(response,customerName);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> uList = us.getUserList();
        request.setAttribute("uList",uList);
        request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request,response);
    }
}
