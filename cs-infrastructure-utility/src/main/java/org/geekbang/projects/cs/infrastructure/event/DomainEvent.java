package org.geekbang.projects.cs.infrastructure.event;

import lombok.Data;

@Data
public abstract class DomainEvent<T> extends BaseEvent {

    //事件类型
    private String type;
    //事件所对应的操作
    private String operation;
    //事件对应的领域模型
    private T message;
}
