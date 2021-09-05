package cn.edu.hhu.crm.workbench.dao;

import cn.edu.hhu.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    List<ClueRemark> getRemarkByClueId(String clueId);

    int deleteByClueId(String clueId);
}
