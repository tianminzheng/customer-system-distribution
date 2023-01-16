package org.geekbang.projects.cs.servicebus.filter;


import org.geekbang.projects.cs.integration.domain.PlatformCustomerStaff;

public interface CustomerStaffFilter {

    PlatformCustomerStaff execute(PlatformCustomerStaff customerStaff);

    void setNext(CustomerStaffFilter filter);

    CustomerStaffFilter getNext();

    CustomerStaffFilter getLast();
}
