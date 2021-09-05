package cn.edu.hhu.crm.workbench.service;

import cn.edu.hhu.crm.workbench.domain.Customer;
import cn.edu.hhu.crm.workbench.domain.Tran;
import cn.edu.hhu.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TransService {
    List<String> getCustomerName(String name);

    boolean save(Tran t, String customerName);

    Tran getInfoById(String id);

    List<TranHistory> getHistroyList(String id);

    boolean changeStage(Tran t, TranHistory th);

    Map<String,Object> getCharts();
}
