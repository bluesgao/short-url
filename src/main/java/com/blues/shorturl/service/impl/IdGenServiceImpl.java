package com.blues.shorturl.service.impl;

import com.blues.shorturl.entity.IdSegment;
import com.blues.shorturl.service.IdGenService;
import com.blues.shorturl.service.IdSegmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class IdGenServiceImpl implements IdGenService {
    private volatile IdSegment currentIdSegment;
    //private volatile IdSegment nextIdSegment;
    //private volatile IdSegment[] idSegments;//双号段
    //private volatile boolean sw = false;//切换标志
    //private ExecutorService executorService = Executors.newSingleThreadExecutor(new NamedThreadFactory("id-gen"));
    private ReentrantLock lock = new ReentrantLock();

    @Resource
    private IdSegmentService idSegmentService;

    @Override
    public Long getId(String bizTag) {
        if (needLoadNextSegment()) {
            try {
                lock.lock();
                if (needLoadNextSegment()) {
                    currentIdSegment = idSegmentService.getNextIdSegment(bizTag);
                    log.info("loadCurrentIdSegment currentIdSegment:{}", currentIdSegment);
                }
            } finally {
                lock.unlock();
            }
        }

        return currentIdSegment.getCurrentId().getAndIncrement();
    }

    private boolean needLoadNextSegment() {
        return currentIdSegment == null ||
                currentIdSegment.getCurrentId().longValue() >= currentIdSegment.getEndId();
    }

/*    private boolean needSwitchIdSegment() {
        return currentIdSegment.getCurrentId().longValue() == currentIdSegment.getEndId();
    }*/

/*    private void switchIdSegment() {
        try {
            lock.lock();
            currentIdSegment = nextIdSegment;
        } finally {
            lock.unlock();
        }
    }*/

/*    private void loadCurrentIdSegment(String bizTag) {
        if (needLoadNextSegment()) {
            try {
                lock.lock();
                if (needLoadNextSegment()) {
                    currentIdSegment = idSegmentService.getNextIdSegment(bizTag);
                }
            } finally {
                lock.unlock();
                log.info("loadCurrentIdSegment currentIdSegment:{}", currentIdSegment);
            }
        }
    }*/

/*    private void loadNextIdSegment(String bizTag) {
        if (nextIdSegment == null) {
            try {
                lock.lock();
                if (nextIdSegment == null) {
                    executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // 无论获取下个segmentId成功与否，都要将isLoadingNext赋值为false
                                nextIdSegment = idSegmentService.getNextIdSegment(bizTag);
                            } finally {
                                log.info("loadNextIdSegment nextIdSegment:{}", nextIdSegment);
                            }
                        }
                    });
                }
            } finally {
                lock.unlock();
            }
        }
    }*/
}
