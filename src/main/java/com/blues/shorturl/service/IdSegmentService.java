package com.blues.shorturl.service;

import com.blues.shorturl.entity.IdSegment;

public interface IdSegmentService {
    IdSegment getNextIdSegment(String bizTag);
}
