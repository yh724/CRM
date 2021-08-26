package cn.edu.hhu.crm.settings.test;

import cn.edu.hhu.crm.utils.MD5Util;

public class Test01 {
    public static void main(String[] args) {
        String str = MD5Util.getMD5("printf_jk5959");
        System.out.println(str);
    }
}
