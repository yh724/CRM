package cn.edu.hhu.crm.workbench.service;

import cn.edu.hhu.crm.vo.PaginationVo;
import cn.edu.hhu.crm.workbench.domain.Activity;
import cn.edu.hhu.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    boolean saveActivity(Activity activity);

    PaginationVo<Activity> getPageList(Map<String, Object> map);

    boolean deleteActivity(String[] ids);

    Map<String, Object> getActivityInfo(String id);

    boolean updateActivity(Activity activity);

    Activity detail(String id);

    List<ActivityRemark> getRemarkById(String id);

    boolean deleteRemark(String id);

    boolean saveRemark(ActivityRemark ar);

    boolean updateRemark(ActivityRemark ar);
}
