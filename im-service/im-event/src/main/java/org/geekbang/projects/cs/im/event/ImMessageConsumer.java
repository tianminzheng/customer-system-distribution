package org.geekbang.projects.cs.im.event;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.geekbang.projects.cs.im.service.ImMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "consumer_group_im", topic = "topic_im")
public class ImMessageConsumer implements RocketMQListener<ImMessageCreatedEvent> {

    @Autowired
    private ImMessageService imMessageService;

    @Override
    public void onMessage(ImMessageCreatedEvent message) {
        System.out.println("Received message : " + message);

        imMessageService.saveImMessage(message.getMessage());
    }
}
