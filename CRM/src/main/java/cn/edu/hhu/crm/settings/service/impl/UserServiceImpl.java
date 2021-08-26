package cn.edu.hhu.crm.settings.service.impl;

import cn.edu.hhu.crm.exception.LoginException;
import cn.edu.hhu.crm.settings.dao.UserDao;
import cn.edu.hhu.crm.settings.domain.User;
import cn.edu.hhu.crm.settings.service.UserService;
import cn.edu.hhu.crm.utils.DateTimeUtil;
import cn.edu.hhu.crm.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String loginAct, String loginPwd, String loginIp) throws LoginException {
        System.out.println("进入service层");
        Map<String,String> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        User user = userDao.login(map);
        if(user == null)
            throw new LoginException("账号或者密码错误！");
        if("0".equals(user.getLockState()))
            throw new LoginException("账号被屏蔽！");
        if(DateTimeUtil.getSysTime().compareTo(user.getExpireTime()) > 0)
            throw new LoginException("账号已失效！");
        if(!user.getAllowIps().contains(loginIp))
            throw new LoginException("账号ip被屏蔽！");
        return user;
    }

    @Override
    public List<User> getUserList() {
        List<User> uList = userDao.getUserList();
        return uList;
    }
}
