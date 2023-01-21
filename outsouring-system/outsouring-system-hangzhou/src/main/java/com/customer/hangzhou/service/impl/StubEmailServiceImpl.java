package com.customer.hangzhou.service.impl;

import com.customer.hangzhou.event.CustomerStaffSyncEvent;
import com.customer.hangzhou.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class StubEmailServiceImpl implements EmailService {

    @Override
    @EventListener
    @Async
    public void placeCustomerStaffSyncNotice(CustomerStaffSyncEvent event) {
        //这里执行具体邮件发送操作
        System.out.println("发送邮件: " + event.getEventContent());
    }

    @EventListener
    public void placeAnotherCustomerStaffSyncNotice(CustomerStaffSyncEvent event) {
        //这里执行另一种执行操作
        System.out.println("另一种操作: " + event.getEventContent());
    }
}
