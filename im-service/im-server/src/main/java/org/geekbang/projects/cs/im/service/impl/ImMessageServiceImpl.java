package org.geekbang.projects.cs.im.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geekbang.projects.cs.im.entity.ImMessage;
import org.geekbang.projects.cs.im.mapper.ImMessageMapper;
import org.geekbang.projects.cs.im.service.ImMessageService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ImMessageServiceImpl extends ServiceImpl<ImMessageMapper, ImMessage> implements ImMessageService {

    ExecutorService pool = Executors.newFixedThreadPool(2);

    @Override
    public void saveImMessage(ImMessage imMessage) {
        pool.submit(new ImMessageTask(imMessage));
    }

    class ImMessageTask implements Runnable {

        private ImMessage imMessage;

        public ImMessageTask(ImMessage imMessage) {
            this.imMessage = imMessage;
        }

        @Override
        public void run() {
            save(imMessage);
        }
    }
}
