package org.geekbang.projects.cs.ticket.controller;

import org.geekbang.projects.cs.infrastructure.vo.Result;
import org.geekbang.projects.cs.ticket.controller.vo.AddTicketReqVO;
import org.geekbang.projects.cs.ticket.service.ICustomerTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 客服工单表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/customerTickets")
public class CustomerTicketController {

    @Autowired
    private ICustomerTicketService customerTicketService;

    @PostMapping(value = "/")
    Result<Boolean> insertTicket(@RequestBody AddTicketReqVO addTicketReqVO) {

        customerTicketService.insertTicket(addTicketReqVO);
        return Result.success(true);
    }
}
