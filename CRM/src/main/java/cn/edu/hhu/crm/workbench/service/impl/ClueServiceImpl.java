package cn.edu.hhu.crm.workbench.service.impl;

import cn.edu.hhu.crm.utils.DateTimeUtil;
import cn.edu.hhu.crm.utils.SqlSessionUtil;
import cn.edu.hhu.crm.utils.UUIDUtil;
import cn.edu.hhu.crm.vo.PaginationVo;
import cn.edu.hhu.crm.workbench.dao.*;
import cn.edu.hhu.crm.workbench.domain.*;
import cn.edu.hhu.crm.workbench.service.ClueService;

import java.util.List;
import java.util.Map;

public class ClueServiceImpl implements ClueService {
    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private ClueRemarkDao clueRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);
    private ContactsRemarkDao contactsRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ContactsRemarkDao.class);
    private CustomerRemarkDao customerRemarkDao = SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);
    private ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);
    private ContactsActivityRelationDao contactsActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationDao.class);
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    private ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);

    @Override
    public boolean saveClue(Clue clue) {
        boolean flag = true;
        int res = clueDao.saveClue(clue);
        if(res != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public PaginationVo<Clue> getPageList(Map<String, Object> map) {
        int total = clueDao.getClueCountByConditon(map);
        List<Clue> cList = clueDao.getClueListByCondition(map);
        PaginationVo<Clue> pvo = new PaginationVo<>();
        pvo.setActList(cList);
        pvo.setNum(total);
        return pvo;
    }

    @Override
    public Clue getClueById(String clueId) {
        Clue clue = clueDao.getClueById(clueId);
        return clue;
    }

    @Override
    public List<Activity> getActivityByClueId(String clueId) {
        List<Activity> alist = clueDao.getActivityByClueId(clueId);
        return alist;
    }

    @Override
    public boolean unbundle(String id) {
        boolean flag = true;
        int res = clueActivityRelationDao.deleteRelationById(id);
        if(res != 1){
            flag =false;
        }
        return flag;
    }

    @Override
    public boolean bundle(String clueId, String[] activityIds) {
        boolean flag = true;
        for (int i = 0; i < activityIds.length; i++) {
            String id = UUIDUtil.getUUID();
            ClueActivityRelation car = new ClueActivityRelation();
            car.setId(id);
            car.setClueId(clueId);
            car.setActivityId(activityIds[i]);
            int res = clueActivityRelationDao.insertOne(car);
            if(res != 1){
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public boolean convert2Customer(Tran tran, String clueId,String createBy) {
        boolean flag = true;
        String createTime = DateTimeUtil.getSysTime();
        //(1) ???????????????id???????????????id??????????????????????????????????????????????????????????????????
        Clue clue = clueDao.getClueById2(clueId);
        //(2) ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        String company = clue.getCompany();
        Customer customer = customerDao.getCustomerByName(company);
        if(customer == null){
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setOwner(clue.getOwner());
            customer.setName(clue.getCompany());
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setCreateBy(createBy);
            customer.setCreateTime(createTime);
            customer.setContactSummary(clue.getContactSummary());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setDescription(clue.getDescription());
            customer.setAddress(clue.getAddress());
            int res1 = customerDao.insertOne(customer);
            if(res1 != 1){
                flag = false;
            }
            System.out.println("??????????????????!");
        }
        // (3) ?????????????????????????????????????????????????????????
        Contacts contacts = new Contacts();
        contacts.setOwner(clue.getOwner());
        contacts.setId(UUIDUtil.getUUID());
        contacts.setSource(clue.getSource());
        contacts.setFullname(clue.getFullname());
        contacts.setAppellation(clue.getAppellation());
        contacts.setEmail(clue.getEmail());
        contacts.setMphone(clue.getEmail());
        contacts.setJob(clue.getJob());
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(createTime);
        contacts.setCustomerId(customer.getId());
        int res2 = contactsDao.insertOne(contacts);
        if(res2 != 1){
            flag = false;
        }
        System.out.println("????????????????????????");

        //(4) ??????????????????????????????????????????????????????
        List<ClueRemark> clueRemarks = clueRemarkDao.getRemarkByClueId(clueId);
        for(ClueRemark cr:clueRemarks){
            String noteContent = cr.getNoteContent();
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setNoteContent(noteContent);
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(createTime);
            customerRemark.setCustomerId(customer.getId());
            customerRemark.setEditFlag("0");
            int res3 = customerRemarkDao.insertOne(customerRemark);
            if(res3 != 1){
                flag = false;
            }
            System.out.println("???????????????????????????");

            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setNoteContent(noteContent);
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setContactsId(contacts.getId());
            contactsRemark.setEditFlag("0");
            int res4 = contactsRemarkDao.insertOne(contactsRemark);
            if(res4 != 1){
                flag = false;
            }
            System.out.println("??????????????????????????????");
        }

        //(5) ????????????????????????????????????????????????????????????????????????????????????
        List<ClueActivityRelation> clueActivityRelations = clueActivityRelationDao.getRelationsByClueId(clueId);
        for(ClueActivityRelation car : clueActivityRelations){
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setContactsId(contacts.getId());
            contactsActivityRelation.setActivityId(car.getActivityId());
            int res5 = contactsActivityRelationDao.insertOne(contactsActivityRelation);
            if(res5 != 1){
                flag = false;
            }
            System.out.println("????????????????????????????????????");
        }
        //(6) ????????????????????????????????????????????????
        //??????tran??????????????????
        if(tran != null){
            tran.setType("?????????");
            tran.setSource(contacts.getSource());
            tran.setCustomerId(customer.getId());
            tran.setContactsId(contacts.getId());
            tran.setOwner(contacts.getOwner());
            tran.setNextContactTime(contacts.getNextContactTime());
            tran.setDescription(contacts.getDescription());
            tran.setContactSummary(contacts.getContactSummary());
            int res6 = tranDao.insertOne(tran);
            if(res6 != 1){
                flag = false;
            }
            System.out.println("?????????????????????");
            //(7) ??????????????????????????????????????????????????????????????????
            TranHistory th = new TranHistory();
            th.setId(UUIDUtil.getUUID());
            th.setStage(tran.getStage());
            th.setMoney(tran.getMoney());
            th.setExpectedDate(tran.getExpectedDate());
            th.setCreateTime(createTime);
            th.setCreateBy(createBy);
            th.setTranId(tran.getId());
            int res7 = tranHistoryDao.insertOne(th);
            if(res7 != 1){
                flag = false;
            }
            System.out.println("???????????????????????????");
        }

        //(8) ??????????????????
        for(ClueRemark cr:clueRemarks){
            int res8 = clueRemarkDao.deleteByClueId(clueId);
            if(res8 != 1){
                flag = false;
            }
            System.out.println("???????????????????????????");
        }
        //(9) ????????????????????????????????????
        for(ClueActivityRelation car : clueActivityRelations){
            int res9 = clueActivityRelationDao.deleteByClueId(clueId);
            if(res9 != 1){
                flag = false;
            }
            System.out.println("??????????????????????????????????????????");
        }
        //(10) ????????????
        //??????clueId????????????
        int res10 = clueDao.deleteClueById(clueId);
        if(res10 != 1){
            flag = false;
        }
        System.out.println("?????????????????????");

        return flag;
    }



}
