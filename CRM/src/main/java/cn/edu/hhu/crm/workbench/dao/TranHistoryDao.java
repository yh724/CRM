package cn.edu.hhu.crm.workbench.dao;

import cn.edu.hhu.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranHistoryDao {

    int insertOne(TranHistory th);

    List<TranHistory> getHistroyListByClueId(String id);

    Integer getTotal();

    List<Map<String,Object>> getGroupByList();

}
