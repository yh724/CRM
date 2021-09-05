package cn.edu.hhu.crm.workbench.dao;

import cn.edu.hhu.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {

    int insertOne(Customer customer);

    Customer getCustomerByName(String company);

    List<String> getCustomerByChars(String name);
}
