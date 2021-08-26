package cn.edu.hhu.crm.settings.service;

import cn.edu.hhu.crm.exception.LoginException;
import cn.edu.hhu.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User login(String loginAct, String loginPwd, String loginIp) throws LoginException;

    List<User> getUserList();
}
