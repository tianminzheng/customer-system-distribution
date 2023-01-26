package org.geekbang.projects.cs.ticket.event;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.geekbang.projects.cs.ticket.entity.LocalCustomerStaff;
import org.geekbang.projects.cs.ticket.service.ILocalCustomerStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "consumer_group_staff_tag", topic = "topic_staff", selectorExpression = "STAFF")
public class CustomerStaffWithTagConsumer implements RocketMQListener<CustomerStaffChangedEvent> {

    @Autowired
    @Qualifier("redis")
    private ILocalCustomerStaffService localCustomerStaffService;

    @Override
    public void onMessage(CustomerStaffChangedEvent message) {
        System.out.println("Received message : " + message);

        CustomerStaffEventDTO dto = message.getMessage();
        LocalCustomerStaff localCustomerStaff = new LocalCustomerStaff();

        convertLocalCustomerStaff(dto, localCustomerStaff);

        String operation = message.getOperation();
        if(operation.equals("CREATE")) {
            localCustomerStaffService.insertLocalCustomerStaff(localCustomerStaff);
        } else if(operation.equals("UPDATE")) {
            localCustomerStaffService.updateLocalCustomerStaff(localCustomerStaff);
        } else if(operation.equals("DELETE")) {
            localCustomerStaffService.deleteLocalCustomerStaff(localCustomerStaff);
        }
    }

    private void convertLocalCustomerStaff(CustomerStaffEventDTO dto, LocalCustomerStaff localCustomerStaff) {
        localCustomerStaff.setStaffId(dto.getId());
        localCustomerStaff.setStaffName(dto.getStaffName());
        localCustomerStaff.setAccountId(dto.getAccountId());
        localCustomerStaff.setPhone(dto.getPhone());
    }
}
