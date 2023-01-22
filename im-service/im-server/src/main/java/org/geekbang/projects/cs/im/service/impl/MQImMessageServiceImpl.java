package org.geekbang.projects.cs.im.service.impl;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.geekbang.projects.cs.im.entity.ImMessage;
import org.geekbang.projects.cs.im.event.IMMessageCreatedEvent;
import org.geekbang.projects.cs.im.service.ImMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("event")
public class MQImMessageServiceImpl implements ImMessageService {

    private final String topic = "topic_im";

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void saveImMessage(ImMessage imMessage) {
        IMMessageCreatedEvent event = new IMMessageCreatedEvent();
        event.setMessage(imMessage);
        event.setType("IM");
        event.setOperation("CREATE");

        rocketMQTemplate.convertAndSend(topic, event);
    }
}
