package org.geekbang.projects.cs.rpc;

import org.apache.dubbo.config.annotation.DubboService;
import org.geekbang.projects.cs.integration.domain.OutsourcingSystemDTO;
import org.geekbang.projects.cs.integration.domain.PlatformCustomerStaff;
import org.geekbang.projects.cs.integration.service.CustomerStaffIntegrationService;
import org.geekbang.projects.cs.servicebus.endpoint.CustomerStaffEndpoint;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DubboService(version = "${integration.service.version}")
public class CustomerStaffIntegrationServiceImpl implements CustomerStaffIntegrationService {

    @Autowired
    CustomerStaffEndpoint customerStaffEndpoint;

    @Override
    public List<PlatformCustomerStaff> fetchCustomerStaffs(OutsourcingSystemDTO outsourcingSystemDTO) {

        return customerStaffEndpoint.fetchCustomerStaffs(outsourcingSystemDTO);
    }
}
