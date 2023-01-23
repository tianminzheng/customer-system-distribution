package org.geekbang.projects.cs.ticket.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geekbang.projects.cs.infrastructure.exception.BizException;
import org.geekbang.projects.cs.infrastructure.id.DistributedId;
import org.geekbang.projects.cs.ticket.controller.vo.AddTicketReqVO;
import org.geekbang.projects.cs.ticket.converter.CustomerTicketConverter;
import org.geekbang.projects.cs.ticket.entity.CustomerTicket;
import org.geekbang.projects.cs.ticket.mapper.CustomerTicketMapper;
import org.geekbang.projects.cs.ticket.service.ICustomerTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 客服工单表 服务实现类
 * </p>
 */
@Service
public class CustomerTicketServiceImpl extends ServiceImpl<CustomerTicketMapper, CustomerTicket> implements ICustomerTicketService {

    @Autowired
    CustomerTicketMapper customerTicketMapper;

    @Override
    @Transactional
    public void insertTicket(AddTicketReqVO addTicketReqVO) throws BizException {

        CustomerTicket customerTicket = CustomerTicketConverter.INSTANCE.convertVO(addTicketReqVO);
        customerTicket.setTicketNo(DistributedId.getInstance().getFastSimpleUUID());

        //TODO：验证输入参数的正确性
        customerTicketMapper.insert(customerTicket);
    }
}
