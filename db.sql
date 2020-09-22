DROP TABLE IF EXISTS `t_id_segment_cfg`;
CREATE TABLE `t_id_segment_cfg` (
  `id`           bigint(20)   NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `biz_tag`      varchar(64)  NOT NULL
  COMMENT '业务标识',
  `start_id`     bigint(20)   NOT NULL DEFAULT '1'
  COMMENT '起始id',
  `step`         int(11)      NOT NULL
  COMMENT '步长',
  `version`      bigint(20)   NOT NULL DEFAULT '0'
  COMMENT '版本号',
  `description`  varchar(128) NOT NULL
  COMMENT '描述',
  `created_time` datetime              DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `updated_time` datetime              DEFAULT CURRENT_TIMESTAMP
  ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_biz_tag` (`biz_tag`) USING BTREE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  COMMENT ='ID号段配置表';

INSERT INTO `t_id_segment_cfg` (`biz_tag`, `start_id`, `step`, `description`) VALUES ('short_url', 1, 100000, '短链');


DROP TABLE IF EXISTS `t_url_mapping`;
CREATE TABLE `t_url_mapping` (
  `id`             bigint(20)   NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `keyword`        varchar(64)  NOT NULL
  COMMENT '唯一标识',
  `biz_type`       varchar(32)  NOT NULL
  COMMENT '业务标识',
  `origin_url`     varchar(256) NOT NULL
  COMMENT '原始url',
  `origin_url_md5` varchar(64)  NOT NULL
  COMMENT '原始urlMd5',
  `description`    varchar(128) NOT NULL
  COMMENT '描述',
  `creator`        varchar(64)  NOT NULL
  COMMENT '创建者',
  `created_time`   datetime              DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `updated_time`   datetime              DEFAULT CURRENT_TIMESTAMP
  ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_keyword` (`keyword`) USING BTREE,
  KEY `idx_origin_url_md5`(`origin_url_md5`) USING BTREE,
  KEY `idx_biz_type_keyword_md5`(`biz_type`, `keyword`, `origin_url_md5`) USING BTREE

)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  COMMENT ='url映射表';

DROP TABLE IF EXISTS `t_access_control`;
CREATE TABLE `t_access_control` (
  `id`           bigint(20)   NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `biz_type`      varchar(64)  NOT NULL
  COMMENT '业务标识',
  `token`     varchar(64)   NOT NULL
  COMMENT '访问标识',
  `description`  varchar(128) NOT NULL
  COMMENT '描述',
  `created_time` datetime              DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `updated_time` datetime              DEFAULT CURRENT_TIMESTAMP
  ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_biz_type_token` (`biz_type`,`token`) USING BTREE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  COMMENT ='访问控制表';