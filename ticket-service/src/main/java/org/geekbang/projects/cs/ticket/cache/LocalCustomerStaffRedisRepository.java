package org.geekbang.projects.cs.ticket.cache;

import org.geekbang.projects.cs.ticket.entity.LocalCustomerStaff;

public interface LocalCustomerStaffRedisRepository {

    void saveLocalCustomerStaff(LocalCustomerStaff localCustomerStaff);

    void updateLocalCustomerStaff(LocalCustomerStaff localCustomerStaff);

    void deleteLocalCustomerStaff(String staffId);

    LocalCustomerStaff findLocalCustomerStaffByStaffId(String staffId);

    void saveEmptyLocalCustomerStaff(String staffId);
}
