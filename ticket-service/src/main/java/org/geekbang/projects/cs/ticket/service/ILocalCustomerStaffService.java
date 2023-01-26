package org.geekbang.projects.cs.ticket.service;

import org.geekbang.projects.cs.ticket.entity.LocalCustomerStaff;

public interface ILocalCustomerStaffService {

    void insertLocalCustomerStaff(LocalCustomerStaff localCustomerStaff);

    void updateLocalCustomerStaff(LocalCustomerStaff localCustomerStaff);

    void deleteLocalCustomerStaff(LocalCustomerStaff localCustomerStaff);

    LocalCustomerStaff findLocalCustomerStaffByStaffId(Long staffId);
}
