package org.geekbang.projects.cs.servicebus.endpoint;

import org.geekbang.projects.cs.integration.domain.OutsourcingSystemDTO;
import org.geekbang.projects.cs.integration.domain.PlatformCustomerStaff;

import java.util.List;

public interface CustomerStaffEndpoint {

    List<PlatformCustomerStaff> fetchCustomerStaffs(OutsourcingSystemDTO outsourcingSystem);
}
