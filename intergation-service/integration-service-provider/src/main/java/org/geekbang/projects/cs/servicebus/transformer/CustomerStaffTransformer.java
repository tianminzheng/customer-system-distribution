package org.geekbang.projects.cs.servicebus.transformer;

import org.geekbang.projects.cs.integration.domain.PlatformCustomerStaff;

import java.util.List;

public interface CustomerStaffTransformer<T> {

    List<PlatformCustomerStaff> transformCustomerStaffs(List<T> customerStaffs);
}
