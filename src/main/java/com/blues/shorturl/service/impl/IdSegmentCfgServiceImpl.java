package com.blues.shorturl.service.impl;

import com.blues.shorturl.dao.IdSegmentCfgMapper;
import com.blues.shorturl.entity.IdSegmentCfg;
import com.blues.shorturl.service.IdSegmentCfgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class IdSegmentCfgServiceImpl implements IdSegmentCfgService {
    @Resource
    private IdSegmentCfgMapper mapper;

    @Override
    public int createIdCfg(IdSegmentCfg idSegmentCfg) {
        return mapper.insert(idSegmentCfg);
    }

    @Override
    public IdSegmentCfg queryByBizTag(String bizTag) {
        return mapper.selectByBizTag(bizTag);
    }

    @Override
    public int updateByBizTag(IdSegmentCfg idSegmentCfg) {
        return mapper.updateByBizTag(idSegmentCfg);
    }
}
