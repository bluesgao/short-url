package com.blues.shorturl.service;

import com.blues.shorturl.entity.IdSegmentCfg;

public interface IdSegmentCfgService {
    int createIdCfg(IdSegmentCfg idSegmentCfg);

    IdSegmentCfg queryByBizTag(String bizTag);

    int updateByBizTag(IdSegmentCfg idSegmentCfg);
}
