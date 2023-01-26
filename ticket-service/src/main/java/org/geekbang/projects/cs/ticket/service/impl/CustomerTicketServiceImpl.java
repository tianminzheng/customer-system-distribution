package org.geekbang.projects.cs.ticket.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geekbang.projects.cs.infrastructure.exception.BizException;
import org.geekbang.projects.cs.infrastructure.exception.MessageCode;
import org.geekbang.projects.cs.infrastructure.id.DistributedId;
import org.geekbang.projects.cs.ticket.controller.vo.AddTicketReqVO;
import org.geekbang.projects.cs.ticket.converter.CustomerTicketConverter;
import org.geekbang.projects.cs.ticket.entity.CustomerTicket;
import org.geekbang.projects.cs.ticket.entity.LocalCustomerStaff;
import org.geekbang.projects.cs.ticket.mapper.CustomerTicketMapper;
import org.geekbang.projects.cs.ticket.service.ICustomerTicketService;
import org.geekbang.projects.cs.ticket.service.ILocalCustomerStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * <p>
 * 客服工单表 服务实现类
 * </p>
 */
@Service
public class CustomerTicketServiceImpl extends ServiceImpl<CustomerTicketMapper, CustomerTicket> implements ICustomerTicketService {

    @Autowired
    CustomerTicketMapper customerTicketMapper;

    @Autowired
    @Qualifier("redis")
    ILocalCustomerStaffService localCustomerStaffService;

    @Override
    @Transactional
    public void insertTicket(AddTicketReqVO addTicketReqVO) throws BizException {

        CustomerTicket customerTicket = CustomerTicketConverter.INSTANCE.convertVO(addTicketReqVO);
        customerTicket.setTicketNo(DistributedId.getInstance().getFastSimpleUUID());

        Long staffId = addTicketReqVO.getStaffId();

        //验证输入参数StaffId的正确性
        if(Objects.isNull(staffId)) {
            throw new BizException(MessageCode.CHECK_ERROR, "客服编号不能为空");
        }

        LocalCustomerStaff localCustomerStaff = localCustomerStaffService.findLocalCustomerStaffByStaffId(staffId);
        if(Objects.isNull(localCustomerStaff)) {
            throw new BizException(MessageCode.CHECK_ERROR, String.format("客服编号为%s的客服人员不存在", staffId));
        }

        customerTicketMapper.insert(customerTicket);
    }
}
