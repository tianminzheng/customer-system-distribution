package org.geekbang.projects.cs.im.event;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.geekbang.projects.cs.im.service.ImMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "consumer_group_im_delay", topic = "topic_im_delay")
public class ImMessageWithDelayConsumer implements RocketMQListener<ImMessageCreatedEvent> {

    @Autowired
    private ImMessageService imMessageService;

    @Override
    public void onMessage(ImMessageCreatedEvent message) {
        System.out.println("消费时间：" + System.currentTimeMillis());
        System.out.println("Received message : " + message);

        imMessageService.saveImMessage(message.getMessage());
    }
}
