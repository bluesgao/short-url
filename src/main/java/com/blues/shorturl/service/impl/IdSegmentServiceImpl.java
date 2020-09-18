package com.blues.shorturl.service.impl;

import com.blues.shorturl.common.Constants;
import com.blues.shorturl.entity.IdSegment;
import com.blues.shorturl.entity.IdSegmentCfg;
import com.blues.shorturl.service.IdSegmentCfgService;
import com.blues.shorturl.service.IdSegmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class IdSegmentServiceImpl implements IdSegmentService {
    @Resource
    private IdSegmentCfgService idSegmentCfgService;

    @Override
    public IdSegment getNextIdSegment(String bizTag) {
        // 获取NextIdSegment的时候，有可能存在version冲突，需要重试
        IdSegment idSegment = new IdSegment();
        for (int i = 0; i < Constants.RETRY; i++) {
            IdSegmentCfg idSegmentCfg = idSegmentCfgService.queryByBizTag(bizTag);
            log.info("getNextIdSegment idSegmentCfg:{}", idSegmentCfg);
            if (idSegmentCfg == null) {
                throw new RuntimeException("can not find bizTag:" + bizTag);
            }
            //更新记录
            int row = idSegmentCfgService.updateByBizTag(idSegmentCfg);
            if (row == 1) {
                idSegment.setStartId(idSegmentCfg.getStartId());
                idSegment.setEndId(idSegmentCfg.getStartId() + idSegmentCfg.getStep());
                idSegment.setMidId(idSegment.getStartId() + (idSegment.getEndId() - idSegment.getStartId()) / 2);
                idSegment.setCurrentId(new AtomicLong(idSegment.getStartId()));
                log.info("getNextIdSegment success idSegmentCfg:{} idSegment:{}", idSegmentCfg, idSegment);
                return idSegment;
            } else {
                log.error("getNextIdSegment conflict idSegmentCfg:{}", idSegmentCfg);
            }
        }
        throw new RuntimeException("get next id segment conflict");
    }
}
