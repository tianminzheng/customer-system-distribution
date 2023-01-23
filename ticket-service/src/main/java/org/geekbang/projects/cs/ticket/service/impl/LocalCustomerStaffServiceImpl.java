package org.geekbang.projects.cs.ticket.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geekbang.projects.cs.infrastructure.exception.BizException;
import org.geekbang.projects.cs.ticket.entity.LocalCustomerStaff;
import org.geekbang.projects.cs.ticket.mapper.LocalCustomerStaffMapper;
import org.geekbang.projects.cs.ticket.service.ILocalCustomerStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocalCustomerStaffServiceImpl extends ServiceImpl<LocalCustomerStaffMapper, LocalCustomerStaff> implements ILocalCustomerStaffService {

    @Autowired
    LocalCustomerStaffMapper localCustomerStaffMapper;

    @Override
    @Transactional
    public void insertLocalCustomerStaff(LocalCustomerStaff localCustomerStaff) throws BizException {

        localCustomerStaffMapper.insert(localCustomerStaff);
    }

    @Override
    public void updateLocalCustomerStaff(LocalCustomerStaff localCustomerStaff) throws BizException {

        localCustomerStaffMapper.updateById(localCustomerStaff);
    }

    @Override
    public void deleteLocalCustomerStaff(LocalCustomerStaff localCustomerStaff) throws BizException {

        localCustomerStaffMapper.deleteById(localCustomerStaff);
    }
}
