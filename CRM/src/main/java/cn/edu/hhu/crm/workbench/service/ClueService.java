package cn.edu.hhu.crm.workbench.service;

import cn.edu.hhu.crm.vo.PaginationVo;
import cn.edu.hhu.crm.workbench.domain.Activity;
import cn.edu.hhu.crm.workbench.domain.Clue;
import cn.edu.hhu.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface ClueService {
    boolean saveClue(Clue clue);

    PaginationVo<Clue> getPageList(Map<String, Object> map);

    Clue getClueById(String clueId);

    List<Activity> getActivityByClueId(String clueId);

    boolean unbundle(String id);

    boolean bundle(String clueId, String[] activityIds);

    boolean convert2Customer(Tran tran, String clueId,String createBy);
}
