package cn.edu.hhu.crm.settings.dao;

import cn.edu.hhu.crm.settings.domain.User;

import java.util.Map;

public interface UserDao {

    User select01(Map<String, String> map);
}
