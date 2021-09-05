package cn.edu.hhu.crm.workbench.dao;

import cn.edu.hhu.crm.workbench.domain.Tran;

public interface TranDao {

    int insertOne(Tran tran);

    Tran getInfoById(String id);

    int updateStage(Tran t);
}
