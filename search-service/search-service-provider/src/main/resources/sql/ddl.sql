CREATE TABLE `customer_auto_reply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `keyword` varchar(45) NOT NULL COMMENT '关键词',
  `content` varchar(255) NOT NULL COMMENT '自动回复内容',
  `sort` int(11) NOT NULL COMMENT '排序',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，1=删除,0=未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='客服自动回复表'


CREATE TABLE `pinned_query_config` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `platform` varchar(50) NOT NULL COMMENT '应用平台，1：APP 2：PC 3：小程序',
  `business_type` tinyint(4) NOT NULL COMMENT '业务类型，1：客服管理 2：其他业务',
  `subject_word` varchar(100) NOT NULL COMMENT '主题词',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `content_ids` varchar(100) NOT NULL COMMENT '内容ID',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：1：待上线 2：使用中 3：已下线',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='置顶搜索配置表'


