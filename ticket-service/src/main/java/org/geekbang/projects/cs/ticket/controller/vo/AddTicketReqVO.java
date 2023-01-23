package org.geekbang.projects.cs.ticket.controller.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class AddTicketReqVO implements Serializable {

    private String ticketNo;

    private Long userId;

    private Long staffId;

    private String inquire;
}
