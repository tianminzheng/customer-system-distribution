package org.geekbang.projects.cs.ticket.cache.bloom;

import com.google.common.hash.BloomFilter;
import org.geekbang.projects.cs.ticket.cache.LocalCustomerStaffRedisRepository;
import org.geekbang.projects.cs.ticket.entity.LocalCustomerStaff;
import org.geekbang.projects.cs.ticket.mapper.LocalCustomerStaffMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class BloomFilterThread implements Runnable {

    private CyclicBarrier barrier;
    private BloomFilter<String> bloomFilter;

    private LocalCustomerStaffMapper localCustomerStaffMapper;
    private LocalCustomerStaffRedisRepository localCustomerStaffRedisRepository;

    public BloomFilterThread(CyclicBarrier barrier,
                             BloomFilter<String> bloomFilter, LocalCustomerStaffMapper localCustomerStaffMapper,
                             LocalCustomerStaffRedisRepository localCustomerStaffRedisRepository) {
        this.barrier = barrier;
        this.bloomFilter = bloomFilter;
        this.localCustomerStaffMapper = localCustomerStaffMapper;
        this.localCustomerStaffRedisRepository = localCustomerStaffRedisRepository;
    }

    @Override
    public void run() {
        try {
            // 等所有线程准备就绪后，一起执行
            barrier.await();
        } catch (Exception e) {
            System.out.println(e);
        }
        String threadName = Thread.currentThread().getName();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //初始化1000个StaffId进行攻击
        Random random = new Random();
        String staffId = String.valueOf(random.nextInt(1000));

        // 先布隆过滤器过滤一把
        if (!bloomFilter.mightContain(staffId)) {
            System.out.println(simpleDateFormat.format(new Date()) + "：布隆过滤器中不存在，疑似非法请求！staffId = " + staffId);
            return;
        }

        // 如果布隆过滤器没有挡住（有可能存在误判），则从Redis中查询数据
        Object value = localCustomerStaffRedisRepository.findLocalCustomerStaffByStaffId(staffId);
        if (value != null) {
            System.out.println(threadName + "," + simpleDateFormat.format(new Date()) + "：缓存命中，staffId = " + staffId);
            return;
        }

        // 如果Redis中没有，则去数据库查询数据
        LocalCustomerStaff localCustomerStaff = null;
        try {
            localCustomerStaff = localCustomerStaffMapper.findLocalCustomerStaffByStaffId(Long.parseLong(staffId));
        } catch (Exception e) {
            System.out.println(e);
        }

        //如果数据库中存在该数据
        if (localCustomerStaff != null) {
            System.out.println(threadName + "," + simpleDateFormat.format(new Date()) + "：在数据库中查到，准备写缓存，" + "staffId = " + staffId);

            // 将数据库查询的数据写入缓存
            localCustomerStaffRedisRepository.saveLocalCustomerStaff(localCustomerStaff);
            System.out.println(threadName + "," + "缓存写入成功！staffId = " + staffId);
        } else {
            // 如果数据库中没有对应数据，则往Redis中写个一个空对象
            System.out.println(threadName + "," + simpleDateFormat.format(new Date()) + "：在Redis未找到,在数据库中也未查到，发生缓存穿透！staffId = " + staffId);
            localCustomerStaffRedisRepository.saveEmptyLocalCustomerStaff(staffId);
        }
    }
}
