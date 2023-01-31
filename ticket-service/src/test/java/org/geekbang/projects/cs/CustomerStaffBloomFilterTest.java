package org.geekbang.projects.cs;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.geekbang.projects.cs.ticket.Application;
import org.geekbang.projects.cs.ticket.cache.bloom.BloomFilterThread;
import org.geekbang.projects.cs.ticket.cache.LocalCustomerStaffRedisRepository;
import org.geekbang.projects.cs.ticket.entity.LocalCustomerStaff;
import org.geekbang.projects.cs.ticket.mapper.LocalCustomerStaffMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public class CustomerStaffBloomFilterTest {

    @Autowired
    private LocalCustomerStaffMapper localCustomerStaffMapper;

    @Autowired
    private LocalCustomerStaffRedisRepository localCustomerStaffRedisRepository;

    public static BloomFilter<String> bloomFilter;

    @Test
    public void count() {
        System.out.println("CustomerStaff数量：" + localCustomerStaffMapper.selectCount(null));
    }

    @PostConstruct
    public void init() {
        long start = System.currentTimeMillis();
        List<LocalCustomerStaff> staffs = localCustomerStaffMapper.selectList(null);
        if (staffs != null && staffs.size() > 0) {
            bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("uTF-8")), staffs.size());
            staffs.forEach(u -> {
                // 将查询（离线的）的所有CustomerStaff全部存入布隆过滤器中
                bloomFilter.put(u.getStaffId().toString());
            });
        }
        long end = System.currentTimeMillis();
        System.out.println("初始化的所有CustomerStaff全部存入布隆过滤器中耗时：" + (end - start) + "ms");
    }

    @Test
    public void customerStaffQueryTest() throws Exception {
        int concurrent = 1000;
        // 利用循环栅栏来实现1000个线程同时并发工作
        CyclicBarrier barrier = new CyclicBarrier(concurrent);

        // 生成1000个固定的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(concurrent);
        long start = System.currentTimeMillis();
        for (int i = 0; i < concurrent; i++) {
            executorService.execute(new BloomFilterThread(barrier, bloomFilter, localCustomerStaffMapper, localCustomerStaffRedisRepository));
        }

        executorService.shutdown();

        // 等待线程池中的任务全部执行完
        while (!executorService.isTerminated()) {
        }
        long end = System.currentTimeMillis();
        System.out.println("1000个线程并发查询数据库，耗时：" + (end - start) + "ms");
    }
}
