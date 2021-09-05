package cn.edu.hhu.crm.workbench.service.impl;

import cn.edu.hhu.crm.utils.SqlSessionUtil;
import cn.edu.hhu.crm.utils.UUIDUtil;
import cn.edu.hhu.crm.workbench.dao.CustomerDao;
import cn.edu.hhu.crm.workbench.dao.TranDao;
import cn.edu.hhu.crm.workbench.dao.TranHistoryDao;
import cn.edu.hhu.crm.workbench.domain.Customer;
import cn.edu.hhu.crm.workbench.domain.Tran;
import cn.edu.hhu.crm.workbench.domain.TranHistory;
import cn.edu.hhu.crm.workbench.service.TransService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransServiceImpl implements TransService {
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);
    @Override
    public List<String> getCustomerName(String name) {
        List<String> cList = customerDao.getCustomerByChars(name);
        return cList;
    }

    @Override
    public boolean save(Tran t, String customerName) {
        boolean flag = true;
        Customer c = customerDao.getCustomerByName(customerName);
        if(c == null){
            c = new Customer();
            c.setId(UUIDUtil.getUUID());
            c.setOwner(t.getOwner());
            c.setName(customerName);
            c.setCreateBy(t.getCreateBy());
            c.setCreateTime(t.getCreateTime());
            c.setDescription(t.getDescription());
            c.setNextContactTime(t.getNextContactTime());
            c.setContactSummary(t.getContactSummary());
            int res1 = customerDao.insertOne(c);
            if(res1 != 1){
                flag = false;
            }
        }
        t.setCustomerId(c.getId());
        int res2 = tranDao.insertOne(t);
        if(res2 != 1){
            flag = false;
        }
        TranHistory th = new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setTranId(t.getId());
        th.setCreateBy(t.getCreateBy());
        th.setCreateTime(t.getCreateTime());
        th.setExpectedDate(t.getExpectedDate());
        th.setStage(t.getStage());
        th.setMoney(t.getMoney());
        int res3 = tranHistoryDao.insertOne(th);
        if(res3 != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public Tran getInfoById(String id) {
        Tran t = tranDao.getInfoById(id);
        return t;
    }

    @Override
    public List<TranHistory> getHistroyList(String id) {
        List<TranHistory> thList = tranHistoryDao.getHistroyListByClueId(id);
        return thList;
    }

    @Override
    public boolean changeStage(Tran t, TranHistory th) {
        boolean flag = true;
        int res1 = tranDao.updateStage(t);
        if(res1 != 1){
            flag = false;
        }
        int res2 = tranHistoryDao.insertOne(th);
        if(res2 != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public Map<String,Object> getCharts() {
        Integer total = tranHistoryDao.getTotal();
        List<Map<String,Object>> mapList = tranHistoryDao.getGroupByList();
        Map<String,Object> resMap = new HashMap<>();
        resMap.put("total",total);
        resMap.put("dataList",mapList);
        return resMap;
    }
}
