package org.geekbang.projects.cs.rpc;

import org.apache.dubbo.config.annotation.DubboService;
import org.geekbang.projects.cs.customer.service.CustomerStaffSyncService;
import org.geekbang.projects.cs.service.ICustomerStaffService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService(version = "${customer.service.version}")
public class CustomerStaffSyncServiceImpl implements CustomerStaffSyncService {

    @Autowired
    ICustomerStaffService customerStaffService;

    @Override
    public void syncOutsourcingCustomerStaffsBySystemId(Long systemId) {
        customerStaffService.syncOutsourcingCustomerStaffsBySystemId(systemId);
    }
}
