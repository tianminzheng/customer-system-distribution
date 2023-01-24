package org.geekbang.projects.cs.event;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.geekbang.projects.cs.entity.staff.CustomerStaff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerStaffChangedEventWithTagProducer {

    private final String TOPIC_STAFF = "topic_staff";

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void sendCustomerStaffChangedEvent(CustomerStaff customerStaff, String operation) {

        CustomerStaffEventDTO customerStaffEventDTO = new CustomerStaffEventDTO();
        customerStaffEventDTO.setId(customerStaff.getId());
        customerStaffEventDTO.setStaffName(customerStaff.getStaffName());
        customerStaffEventDTO.setAccountId(customerStaff.getAccountId());
        customerStaffEventDTO.setPhone(customerStaff.getPhone());

        CustomerStaffChangedEvent event = new CustomerStaffChangedEvent();
        event.setType("STAFF");
        event.setOperation(operation);
        event.setMessage(customerStaffEventDTO);

        //发送一个无效Tag的消息
        String tagOther = "OTHER";
        String destination = String.format("%s:%s", TOPIC_STAFF, tagOther);
        this.rocketMQTemplate.convertAndSend(destination, event);

        //发送一个有效Tag的消息
        String tagStaff = "STAFF";
        destination = String.format("%s:%s", TOPIC_STAFF, tagStaff);
        this.rocketMQTemplate.convertAndSend(destination, event);
    }
}
