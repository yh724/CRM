package cn.edu.hhu.crm.settings.service.impl;

import cn.edu.hhu.crm.settings.dao.DicTypeDao;
import cn.edu.hhu.crm.settings.dao.DicValueDao;
import cn.edu.hhu.crm.settings.domain.DicType;
import cn.edu.hhu.crm.settings.domain.DicValue;
import cn.edu.hhu.crm.settings.service.DicService;
import cn.edu.hhu.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DicServiceImpl implements DicService {
    DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);

    @Override
    public Map<String, List> getDicMap() {
        Map<String,List> map = new HashMap<>();
        List<DicType> dicTypeList = dicTypeDao.getAll();
        for(DicType dt:dicTypeList){
            List<DicValue> dvList = dicValueDao.getAllByType(dt.getCode());
            map.put(dt.getCode(),dvList);
        }
        return map;
    }
}
