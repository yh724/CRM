package cn.edu.hhu.crm.workbench.web.controller;

import cn.edu.hhu.crm.settings.domain.User;
import cn.edu.hhu.crm.settings.service.UserService;
import cn.edu.hhu.crm.settings.service.impl.UserServiceImpl;
import cn.edu.hhu.crm.utils.DateTimeUtil;
import cn.edu.hhu.crm.utils.PrintJson;
import cn.edu.hhu.crm.utils.ServiceFactory;
import cn.edu.hhu.crm.utils.UUIDUtil;
import cn.edu.hhu.crm.vo.PaginationVo;
import cn.edu.hhu.crm.workbench.domain.Activity;
import cn.edu.hhu.crm.workbench.domain.ActivityRemark;
import cn.edu.hhu.crm.workbench.service.ActivityService;
import cn.edu.hhu.crm.workbench.service.impl.ActivityServiceImpl;
import org.apache.taglibs.standard.tag.common.fmt.RequestEncodingSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if("/workbench/activity/getUserList.do".equals(path)){
            getUserList(request,response);
        }else if("/workbench/activity/saveActivity.do".equals(path)){
            System.out.println("执行控制器模板判断"+Thread.currentThread().getName());
            saveActivity(request,response);
        }else if("/workbench/activity/pageList.do".equals(path)){
            pageList(request,response);
        }else if("/workbench/activity/delete.do".equals(path)){
            deleteActivity(request,response);
        }else if("/workbench/activity/getActivityInfo.do".equals(path)){
            getActivityInfo(request,response);
        }else if("/workbench/activity/updateActivity.do".equals(path)){
            updateActivity(request,response);
        }else if("/workbench/activity/detail.do".equals(path)){
            detail(request,response);
        }else if("/workbench/activity/getRemarkList.do".equals(path)){
            getRemarkList(request,response);
        }else if("/workbench/activity/deleteRemark.do".equals(path)){
            deleteRemark(request,response);
        }else if("/workbench/activity/saveRemark.do".equals(path)){
            saveRemark(request,response);
        }else if("/workbench/activity/updateRemark.do".equals(path)){
            updateRemark(request,response);
        }
    }

    private void updateRemark(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String noteContent = request.getParameter("noteContent");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User)request.getSession().getAttribute("user")).getName();
        String editFlag = "1";

        ActivityRemark ar = new ActivityRemark();
        ar.setId(id);
        ar.setNoteContent(noteContent);
        ar.setEditTime(editTime);
        ar.setEditBy(editBy);
        ar.setEditFlag(editFlag);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean success = as.updateRemark(ar);
        PrintJson.printJsonFlag(response,success);

    }

    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {
        String id = UUIDUtil.getUUID();
        String noteContent = request.getParameter("noteContent");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String editFlag  = request.getParameter("0");
        String activityId = request.getParameter("activityId");

        ActivityRemark ar = new ActivityRemark();
        ar.setId(id);
        ar.setNoteContent(noteContent);
        ar.setCreateTime(createTime);
        ar.setCreateBy(createBy);
        ar.setEditFlag(editFlag);
        ar.setActivityId(activityId);
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean success = as.saveRemark(ar);
        Map<String,Object> map = new HashMap<>();
        map.put("success",success);
        map.put("ar",ar);
        PrintJson.printJsonObj(response,map);
    }

    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean success = as.deleteRemark(id);
        PrintJson.printJsonFlag(response,success);
    }

    private void getRemarkList(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<ActivityRemark> arList = as.getRemarkById(id);
        PrintJson.printJsonObj(response,arList);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Activity activity = as.detail(id);
        request.setAttribute("detailAct",activity);
        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);
    }

    private void updateActivity(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String owner = request.getParameter("owner");
        String name  = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User)request.getSession().getAttribute("user")).getName();

        Activity activity = new Activity();
        activity.setCost(cost);
        activity.setStartDate(startDate);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setId(id);
        activity.setEndDate(endDate);
        activity.setDescription(description);
        activity.setEditTime(editTime);
        activity.setEditBy(editBy);
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean success = as.updateActivity(activity);
        PrintJson.printJsonFlag(response,success);
    }

    private void getActivityInfo(HttpServletRequest request, HttpServletResponse response) {
        /**
         * Map<String,Object>
         *
         * */
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Map<String,Object> map = as.getActivityInfo(id);
        PrintJson.printJsonObj(response,map);
    }

    private void deleteActivity(HttpServletRequest request, HttpServletResponse response) {
        String[] ids = request.getParameterValues("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean success = as.deleteActivity(ids);
        PrintJson.printJsonFlag(response,success);
    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        String ownerName = request.getParameter("ownerName");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startTime");
        String endDate = request.getParameter("endTime");
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        int pageSikp = (Integer.parseInt(pageNo)-1)*Integer.parseInt(pageSize);

        Map<String,Object> map = new HashMap<>();
        map.put("ownerName",ownerName);
        map.put("name",name);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("pageSkip",pageSikp);
        map.put("pageSize",Integer.parseInt(pageSize));

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        PaginationVo<Activity> pvo = as.getPageList(map);
        PrintJson.printJsonObj(response,pvo);

    }

    private void saveActivity(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入save控制器"+Thread.currentThread().getName());
        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name  = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();

        Activity activity = new Activity();
        activity.setCost(cost);
        activity.setStartDate(startDate);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setId(id);
        activity.setEndDate(endDate);
        activity.setDescription(description);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean success = as.saveActivity(activity);
        PrintJson.printJsonFlag(response,success);


    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入用户查询控制器");
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> userList = us.getUserList();
        PrintJson.printJsonObj(response,userList);
    }
}
