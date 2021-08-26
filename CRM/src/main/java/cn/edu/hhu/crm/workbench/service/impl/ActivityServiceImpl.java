package cn.edu.hhu.crm.workbench.service.impl;

import cn.edu.hhu.crm.settings.dao.UserDao;
import cn.edu.hhu.crm.settings.domain.User;
import cn.edu.hhu.crm.utils.SqlSessionUtil;
import cn.edu.hhu.crm.vo.PaginationVo;
import cn.edu.hhu.crm.workbench.dao.ActivityDao;
import cn.edu.hhu.crm.workbench.dao.ActivityRemarkDao;
import cn.edu.hhu.crm.workbench.domain.Activity;
import cn.edu.hhu.crm.workbench.domain.ActivityRemark;
import cn.edu.hhu.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public boolean saveActivity(Activity activity) {
        System.out.println("save服务执行了！"+Thread.currentThread().getName());
        int res = activityDao.saveActivity(activity);
        if(res != 1)
            return false;
        return true;
    }

    @Override
    public PaginationVo<Activity> getPageList(Map<String, Object> map) {
        int total = activityDao.getActivityCountByConditon(map);
        List<Activity> aList = activityDao.getActivityListByCondition(map);
        PaginationVo<Activity> pvo = new PaginationVo<>();
        pvo.setActList(aList);
        pvo.setNum(total);
        return pvo;
    }

    @Override
    public boolean deleteActivity(String[] ids) {
        boolean flag = true;

        int count1 = activityRemarkDao.getCountByIds(ids);
        int count2 = activityRemarkDao.deleteRemarkByIds(ids);

        if(count1 != count2){
            flag = false;
        }

        int count3 = activityDao.deleteActivity(ids);
        if(count3 != ids.length)
            flag = false;
        return flag;
    }

    @Override
    public Map<String, Object> getActivityInfo(String id) {
        List<User> uList = userDao.getUserList();
        Activity activity = activityDao.getActivityById(id);
        Map<String,Object> map = new HashMap<>();
        map.put("uList",uList);
        map.put("a",activity);
        return map;
    }

    @Override
    public boolean updateActivity(Activity activity) {
        boolean flag = true;
        int res = activityDao.updateActivity(activity);
        if(res != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public Activity detail(String id) {
        Activity activity = activityDao.detail(id);
        return activity;
    }

    @Override
    public List<ActivityRemark> getRemarkById(String id) {
        List<ActivityRemark> arList = activityRemarkDao.getRemarkById(id);
        return arList;
    }

    @Override
    public boolean deleteRemark(String id) {
        boolean flag = true;
        int res = activityRemarkDao.deleteRemarkById(id);
        if(res != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean saveRemark(ActivityRemark ar) {
        boolean flag = true;
        int res = activityRemarkDao.insertOneRemark(ar);
        if(res != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean updateRemark(ActivityRemark ar) {
        boolean flag = true;
        int res = activityRemarkDao.updateRemark(ar);
        if(res != 1){
            flag = false;
        }
        return flag;
    }
}
