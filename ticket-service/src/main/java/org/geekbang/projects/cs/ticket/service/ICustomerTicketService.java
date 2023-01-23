package org.geekbang.projects.cs.ticket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.geekbang.projects.cs.infrastructure.exception.BizException;
import org.geekbang.projects.cs.ticket.controller.vo.AddTicketReqVO;
import org.geekbang.projects.cs.ticket.entity.CustomerTicket;

/**
 * <p>
 * 客服工单表 服务类
 * </p>
 */
public interface ICustomerTicketService extends IService<CustomerTicket> {

    void insertTicket(AddTicketReqVO addTicketReqVO) throws BizException;
}
