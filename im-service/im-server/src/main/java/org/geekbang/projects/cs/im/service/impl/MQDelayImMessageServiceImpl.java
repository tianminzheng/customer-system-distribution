package org.geekbang.projects.cs.im.service.impl;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.geekbang.projects.cs.im.entity.ImMessage;
import org.geekbang.projects.cs.im.event.ImMessageCreatedEvent;
import org.geekbang.projects.cs.im.service.ImMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service("event_delay")
public class MQDelayImMessageServiceImpl implements ImMessageService {

    private final String TOPIC_IM_DELAY = "topic_im_delay";

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void saveImMessage(ImMessage imMessage) {
        ImMessageCreatedEvent event = new ImMessageCreatedEvent();
        event.setMessage(imMessage);
        event.setType("IM");
        event.setOperation("CREATE");

        //延迟10s
        SendResult result = rocketMQTemplate.syncSend(TOPIC_IM_DELAY, MessageBuilder.withPayload(event).build(), 2000, 3);
        System.out.println("发送时间：" + System.currentTimeMillis());
        System.out.println(result);
    }
}
