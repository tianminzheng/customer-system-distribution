package com.customer.hangzhou.service.impl;

import com.customer.hangzhou.entity.HangzhouCustomerStaff;
import com.customer.hangzhou.event.CustomerStaffSyncEvent;
import com.customer.hangzhou.repository.HangzhouCustomerStaffRepository;
import com.customer.hangzhou.service.HangzhouCustomerStaffService;
import org.geekbang.projects.cs.infrastructure.exception.BizException;
import org.geekbang.projects.cs.infrastructure.exception.MessageCode;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class HangzhouCustomerStaffServiceImpl implements HangzhouCustomerStaffService {

    @Autowired
    HangzhouCustomerStaffRepository customerStaffRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public List<HangzhouCustomerStaff> findAllCustomerStaffs() {

        CustomerStaffSyncEvent customerStaffSyncEvent = new CustomerStaffSyncEvent("findAllCustomerStaffs");

        publisher.publishEvent(customerStaffSyncEvent);

        return customerStaffRepository.findByIsDeletedFalse();
    }

    @Override
    public List<HangzhouCustomerStaff> findCustomerStaffsByUpdatedTime(Date updatedTime) {
        return customerStaffRepository.findByUpdatedAtAfter(updatedTime);
    }

    @Override
    public HangzhouCustomerStaff createCustomerStaff(HangzhouCustomerStaff customerStaff) {
        return customerStaffRepository.save(customerStaff);
    }

    @Override
    public HangzhouCustomerStaff updateCustomerStaff(HangzhouCustomerStaff customerStaff) {
        String lockKey = "HangzhouCustomerStaff" + customerStaff.getId();

        //获取RLock对象，并判空
        RLock lock = redissonClient.getLock(lockKey);
        if (Objects.isNull(lock)) {
            System.out.println("获取锁为空");
            throw new BizException(MessageCode.SYSTEM_ERROR, "获取锁为空");
        }

        //尝试加锁，如果加锁失败则抛异常
        boolean tryLock;
        try {
            //lock会一直阻塞,而tryLock加锁失败，会返回false
            tryLock = lock.tryLock(3, 60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("获取锁异常");
            throw new BizException(MessageCode.SYSTEM_ERROR, "获取锁异常");
        }
        if (!tryLock) {
            System.out.println("没有获取到锁");
            throw new BizException(MessageCode.SYSTEM_ERROR, "没有获取到锁");
        }

        //执行业务代码并释放锁
        try {
            System.out.println("业务处理成功");
            return customerStaffRepository.save(customerStaff);
        } catch (Exception e) {
            System.out.println("业务处理异常");
            throw new BizException(MessageCode.SYSTEM_ERROR, "业务处理异常");
        } finally {
            //判断要解锁的key是否已被锁定,只有加锁成功才需要解锁
            //判断要解锁的key是否被当前线程持有,自己加的锁自己解，不能解别人的锁
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    @Override
    public HangzhouCustomerStaff deleteCustomerStaffById(Long id) {
        HangzhouCustomerStaff customerStaff = new HangzhouCustomerStaff().setId(id).setIsDeleted(true);

        return customerStaffRepository.save(customerStaff);
    }
}
