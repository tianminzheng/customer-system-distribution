package com.customer.hangzhou.controller;

import com.customer.hangzhou.entity.HangzhouCustomerStaff;
import com.customer.hangzhou.service.HangzhouCustomerStaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/customerStaffs/hangzhou/test/")
public class HangzhouCustomerStaffTestController {

    @Autowired
    private HangzhouCustomerStaffService customerStaffService;

    @GetMapping("/")
    public void test() {
        testUpdateCustomerStaff();
    }

    public void testUpdateCustomerStaff() {

        int corePoolSize = 16;
        int maximumPoolSize = 50;
        long KeepAliveTime = 2;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue workQueue = new LinkedBlockingQueue<>(1000);
        ThreadPoolExecutor customThreadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, KeepAliveTime, unit, workQueue);

        for (int i = 0; i < 1000; i++) {
            customThreadPoolExecutor.execute(new UpdateHangzhouCustomerStaffTask(buildCustomerStaff()));
        }
    }

    class UpdateHangzhouCustomerStaffTask extends Thread {

        private HangzhouCustomerStaff customerStaff;

        public UpdateHangzhouCustomerStaffTask(HangzhouCustomerStaff customerStaff) {
            this.customerStaff = customerStaff;
        }

        public void run() {
            customerStaffService.updateCustomerStaff(customerStaff);
        }
    }

    public HangzhouCustomerStaff buildCustomerStaff() {
        HangzhouCustomerStaff customerStaff = new HangzhouCustomerStaff();
        customerStaff.setId(1L);
        customerStaff.setAvatar("avatar.jpg");
        customerStaff.setNickname("tianyalan");
        customerStaff.setGender("MALE");
        customerStaff.setGoodAt("擅长X");
        customerStaff.setRemark("remark");
        customerStaff.setCreatedAt(new Date());
        customerStaff.setUpdatedAt(new Date());
        customerStaff.setIsDeleted(false);

        return customerStaff;
    }
}
