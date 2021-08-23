package cn.edu.hhu.crm.settings.service;

import cn.edu.hhu.crm.settings.domain.User;

import java.util.Map;

public interface UserService {

    User select01(Map<String, String> map);
}
