package org.geekbang.projects.cs.infrastructure.event;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public abstract class BaseEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private String eventId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date eventTime;

	public BaseEvent() {
		this.eventId = "Event" + UUID.randomUUID().toString().toUpperCase();
		this.eventTime = new Date();
	}

	public String getEventId() {
		return eventId;
	}

	public Date getEventTime() {
		return eventTime;
	}
}