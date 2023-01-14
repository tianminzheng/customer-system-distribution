package org.geekbang.projects.cs.integration.service;

import org.geekbang.projects.cs.integration.domain.OutsourcingSystemDTO;
import org.geekbang.projects.cs.integration.domain.PlatformCustomerStaff;

import java.util.List;

public interface CustomerStaffIntegrationService {

    List<PlatformCustomerStaff> fetchCustomerStaffs(OutsourcingSystemDTO outsourcingSystemDTO);
}
