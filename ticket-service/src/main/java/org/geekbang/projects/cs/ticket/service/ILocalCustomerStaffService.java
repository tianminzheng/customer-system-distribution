package org.geekbang.projects.cs.ticket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.geekbang.projects.cs.infrastructure.exception.BizException;
import org.geekbang.projects.cs.ticket.controller.vo.AddTicketReqVO;
import org.geekbang.projects.cs.ticket.entity.CustomerTicket;
import org.geekbang.projects.cs.ticket.entity.LocalCustomerStaff;

public interface ILocalCustomerStaffService extends IService<LocalCustomerStaff> {

    void insertLocalCustomerStaff(LocalCustomerStaff localCustomerStaff) throws BizException;

    void updateLocalCustomerStaff(LocalCustomerStaff localCustomerStaff) throws BizException;

    void deleteLocalCustomerStaff(LocalCustomerStaff localCustomerStaff) throws BizException;
}
