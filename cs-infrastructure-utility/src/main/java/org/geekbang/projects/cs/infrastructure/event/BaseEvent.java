package org.geekbang.projects.cs.infrastructure.event;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public abstract class BaseEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private String eventId;
	private Date eventTime;

	public BaseEvent() {
		this.eventId = "BaseEvent" + UUID.randomUUID().toString().toUpperCase();
		this.eventTime = new Date();
	}

	public String getEventId() {
		return eventId;
	}

	public Date getEventTime() {
		return eventTime;
	}
}