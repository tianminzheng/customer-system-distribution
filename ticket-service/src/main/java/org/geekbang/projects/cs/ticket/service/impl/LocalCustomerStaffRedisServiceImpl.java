package org.geekbang.projects.cs.ticket.service.impl;

import org.geekbang.projects.cs.ticket.cache.LocalCustomerStaffRedisRepository;
import org.geekbang.projects.cs.ticket.entity.LocalCustomerStaff;
import org.geekbang.projects.cs.ticket.service.ILocalCustomerStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service("redis")
@Primary
public class LocalCustomerStaffRedisServiceImpl implements ILocalCustomerStaffService {

    @Autowired
    LocalCustomerStaffRedisRepository localCustomerStaffRedisRepository;

    @Override
    public void insertLocalCustomerStaff(LocalCustomerStaff localCustomerStaff) {

        localCustomerStaffRedisRepository.saveLocalCustomerStaff(localCustomerStaff);
    }

    @Override
    public void updateLocalCustomerStaff(LocalCustomerStaff localCustomerStaff) {

        localCustomerStaffRedisRepository.updateLocalCustomerStaff(localCustomerStaff);
    }

    @Override
    public void deleteLocalCustomerStaff(LocalCustomerStaff localCustomerStaff) {

        localCustomerStaffRedisRepository.deleteLocalCustomerStaff(localCustomerStaff.getStaffId().toString());
    }

    @Override
    public LocalCustomerStaff findLocalCustomerStaffByStaffId(Long staffId) {

        return localCustomerStaffRedisRepository.findLocalCustomerStaffByStaffId(staffId.toString());
    }
}
