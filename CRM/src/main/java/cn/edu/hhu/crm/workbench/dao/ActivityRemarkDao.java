package cn.edu.hhu.crm.workbench.dao;

import cn.edu.hhu.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {

    List<ActivityRemark> getRemarkById(String id);

    int getCountByIds(String[] ids);

    int deleteRemarkByIds(String[] ids);

    int deleteRemarkById(String id);

    int insertOneRemark(ActivityRemark ar);

    int updateRemark(ActivityRemark ar);
}
