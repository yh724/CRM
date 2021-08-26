package cn.edu.hhu.crm.workbench.dao;

import cn.edu.hhu.crm.vo.PaginationVo;
import cn.edu.hhu.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {

    int saveActivity(Activity activity);
    
    int getActivityCountByConditon(Map<String, Object> map);

    List<Activity> getActivityListByCondition(Map<String, Object> map);

    int deleteActivity(String[] ids);

    Activity getActivityById(String id);

    int updateActivity(Activity activity);

    Activity detail(String id);
}
