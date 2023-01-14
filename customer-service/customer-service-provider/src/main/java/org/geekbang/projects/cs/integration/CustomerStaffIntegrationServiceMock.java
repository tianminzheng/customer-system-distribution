package org.geekbang.projects.cs.integration;

import org.geekbang.projects.cs.integration.domain.OutsourcingSystemDTO;
import org.geekbang.projects.cs.integration.domain.PlatformCustomerStaff;
import org.geekbang.projects.cs.integration.service.CustomerStaffIntegrationService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerStaffIntegrationServiceMock implements CustomerStaffIntegrationService {

    @Override
    public List<PlatformCustomerStaff> fetchCustomerStaffs(OutsourcingSystemDTO outsourcingSystemDTO) {
        return null;
    }
}
