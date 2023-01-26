package org.geekbang.projects.cs.ticket.event;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.geekbang.projects.cs.ticket.entity.LocalCustomerStaff;
import org.geekbang.projects.cs.ticket.service.ILocalCustomerStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "consumer_group_staff", topic = "topic_staff")
public class CustomerStaffConsumer implements RocketMQListener<CustomerStaffChangedEvent> {

    @Autowired
    private ILocalCustomerStaffService localCustomerStaffService;

    @Override
    public void onMessage(CustomerStaffChangedEvent message) {
        System.out.println("Received message : " + message);

        //注意：这里和CustomerStaffWithTagConsumer消费逻辑重复，故关闭数据入库操作入库

//        CustomerStaffEventDTO dto = message.getMessage();
//        LocalCustomerStaff localCustomerStaff = new LocalCustomerStaff();
//
//        convertLocalCustomerStaff(dto, localCustomerStaff);
//
//        String operation = message.getOperation();
//        if(operation.equals("CREATE")) {
//            localCustomerStaffService.insertLocalCustomerStaff(localCustomerStaff);
//        } else if(operation.equals("UPDATE")) {
//            localCustomerStaffService.updateLocalCustomerStaff(localCustomerStaff);
//        } else if(operation.equals("DELETE")) {
//            localCustomerStaffService.deleteLocalCustomerStaff(localCustomerStaff);
//        }
    }

    private void convertLocalCustomerStaff(CustomerStaffEventDTO dto, LocalCustomerStaff localCustomerStaff) {
        localCustomerStaff.setStaffId(dto.getId());
        localCustomerStaff.setStaffName(dto.getStaffName());
        localCustomerStaff.setAccountId(dto.getAccountId());
        localCustomerStaff.setPhone(dto.getPhone());
    }
}
