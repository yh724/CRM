package cn.edu.hhu.crm.workbench.dao;

import cn.edu.hhu.crm.workbench.domain.Activity;
import cn.edu.hhu.crm.workbench.domain.Clue;
import cn.edu.hhu.crm.workbench.domain.Customer;

import java.util.List;
import java.util.Map;

public interface ClueDao {


    int saveClue(Clue clue);

    int getClueCountByConditon(Map<String, Object> map);

    List<Clue> getClueListByCondition(Map<String, Object> map);

    Clue getClueById(String clueId);

    List<Activity> getActivityByClueId(String clueId);

    Clue getClueById2(String clueId);

    int deleteClueById(String clueId);

}
