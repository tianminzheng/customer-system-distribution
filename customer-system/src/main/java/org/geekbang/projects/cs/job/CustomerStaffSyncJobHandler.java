package org.geekbang.projects.cs.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.dubbo.config.annotation.DubboReference;
import org.geekbang.projects.cs.customer.service.CustomerStaffSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CustomerStaffSyncJobHandler {

    @DubboReference(version="${customer.service.version}")
    private CustomerStaffSyncService customerStaffSyncService;

    private static Logger logger = LoggerFactory.getLogger(CustomerStaffSyncJobHandler.class);

    @XxlJob("customerStaffSyncJobHandler")
    public ReturnT<String> syncCustomerStaff() throws Exception {
        logger.info(new Date() + "：同步客服信息~");

        String param = XxlJobHelper.getJobParam();
        Long systemId = Long.parseLong(param);

        //触发远程调用，获取客服信息并保存
        customerStaffSyncService.syncOutsourcingCustomerStaffsBySystemId(systemId);

        return ReturnT.SUCCESS;
    }
}
