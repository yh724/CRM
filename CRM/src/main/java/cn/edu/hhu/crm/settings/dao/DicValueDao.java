package cn.edu.hhu.crm.settings.dao;

import cn.edu.hhu.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> getAllByType(String code);
}
