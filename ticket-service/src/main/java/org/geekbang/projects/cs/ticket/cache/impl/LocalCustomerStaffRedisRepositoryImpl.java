package org.geekbang.projects.cs.ticket.cache.impl;

import org.geekbang.projects.cs.ticket.cache.LocalCustomerStaffRedisRepository;
import org.geekbang.projects.cs.ticket.entity.LocalCustomerStaff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LocalCustomerStaffRedisRepositoryImpl implements LocalCustomerStaffRedisRepository {

    @Autowired
    private RedisTemplate<String, LocalCustomerStaff> redisTemplate;

    private static final String HASH_NAME = "Staff:";

    @Override
    public void saveLocalCustomerStaff(LocalCustomerStaff localCustomerStaff) {
        redisTemplate.opsForValue().set(HASH_NAME + localCustomerStaff.getStaffId().toString(), localCustomerStaff);
    }

    @Override
    public void updateLocalCustomerStaff(LocalCustomerStaff localCustomerStaff) {
        redisTemplate.opsForValue().set(HASH_NAME + localCustomerStaff.getStaffId().toString(), localCustomerStaff);
    }

    @Override
    public void deleteLocalCustomerStaff(String staffId) {
        redisTemplate.delete(HASH_NAME + staffId);
    }

    @Override
    public LocalCustomerStaff findLocalCustomerStaffByStaffId(String staffId) {
        return redisTemplate.opsForValue().get(HASH_NAME + staffId);
    }
}
