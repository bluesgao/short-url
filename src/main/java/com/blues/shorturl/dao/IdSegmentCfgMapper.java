package com.blues.shorturl.dao;

import com.blues.shorturl.entity.IdSegmentCfg;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IdSegmentCfgMapper {
    int insert(IdSegmentCfg record);

    IdSegmentCfg selectByBizTag(String bizTag);

    int updateByBizTag(IdSegmentCfg record);
}
