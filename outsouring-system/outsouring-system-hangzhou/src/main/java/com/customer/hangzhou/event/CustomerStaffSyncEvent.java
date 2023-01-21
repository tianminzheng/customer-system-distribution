package com.customer.hangzhou.event;

import lombok.Data;
import org.geekbang.projects.cs.infrastructure.id.DistributedId;

import java.io.Serializable;
import java.util.Date;

@Data
public class CustomerStaffSyncEvent implements Serializable {

    private String eventId;
    private Date eventTime;
    private String eventContent;

    public CustomerStaffSyncEvent(String eventContent) {
        this.eventId = DistributedId.getInstance().getFastSimpleUUID();
        this.eventTime = new Date();
        this.eventContent = eventContent;
    }
}
