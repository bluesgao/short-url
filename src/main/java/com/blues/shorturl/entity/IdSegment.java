package com.blues.shorturl.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

@Data
public class IdSegment implements Serializable {
    private Long startId;
    private Long midId;
    private Long endId;
    private AtomicLong currentId;
}
