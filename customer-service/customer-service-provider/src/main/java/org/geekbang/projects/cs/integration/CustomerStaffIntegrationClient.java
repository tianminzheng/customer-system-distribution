package org.geekbang.projects.cs.integration;

import org.apache.dubbo.config.annotation.DubboReference;
import org.geekbang.projects.cs.entity.staff.CustomerStaff;
import org.geekbang.projects.cs.entity.tenant.OutsourcingSystem;
import org.geekbang.projects.cs.integration.domain.OutsourcingSystemDTO;
import org.geekbang.projects.cs.integration.domain.PlatformCustomerStaff;
import org.geekbang.projects.cs.integration.service.CustomerStaffIntegrationService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerStaffIntegrationClient {

    @DubboReference(version = "${integration.service.version}", timeout = 5000, loadbalance = "roundrobin", retries = 2,
        mock = "org.geekbang.projects.cs.integration.CustomerStaffIntegrationServiceMock")
    private CustomerStaffIntegrationService customerStaffIntegrationService;

    public List<CustomerStaff> getCustomerStaffs(OutsourcingSystem outsourcingSystem) {

        OutsourcingSystemDTO outsourcingSystemDTO = CustomerStaffIntegrationConverter.INSTANCE.convertOutsourcingSystem(outsourcingSystem);

        List<PlatformCustomerStaff> platformCustomerStaffs =customerStaffIntegrationService.fetchCustomerStaffs(outsourcingSystemDTO);

        return CustomerStaffIntegrationConverter.INSTANCE.convertCustomerStaffListDTO(platformCustomerStaffs);
    }
}
