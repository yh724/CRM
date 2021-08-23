package cn.edu.hhu.crm.settings.service.impl;

import cn.edu.hhu.crm.settings.dao.UserDao;
import cn.edu.hhu.crm.settings.domain.User;
import cn.edu.hhu.crm.settings.service.UserService;
import cn.edu.hhu.crm.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.Map;

public class UserServiceImpl implements UserService {
    UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User select01(Map<String, String> map) {
        return userDao.select01(map);
    }
}
