package cn.edu.hhu.crm.workbench.dao;

import cn.edu.hhu.crm.workbench.domain.Activity;
import org.apache.ibatis.annotations.Param;

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

    List<Activity> getUnbundleActivity(@Param("id") String id,
                                       @Param("keywords") String keywords);

    List<Activity> getActivityByName(@Param("aName") String name);
}
