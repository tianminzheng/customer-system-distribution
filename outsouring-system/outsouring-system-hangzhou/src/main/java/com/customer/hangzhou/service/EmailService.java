package com.customer.hangzhou.service;

import com.customer.hangzhou.event.CustomerStaffSyncEvent;

public interface EmailService {

    void placeCustomerStaffSyncNotice(CustomerStaffSyncEvent event);
}
