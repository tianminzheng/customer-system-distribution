CREATE TABLE `customer_ticket` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ticket_no` varchar(64) NOT NULL COMMENT '工单编号',
  `inquire` varchar(255) NOT NULL COMMENT '工单咨询内容',
  `user_id` bigint(20) NOT NULL COMMENT '用户Id',
  `staff_id` bigint(20) NOT NULL COMMENT '客服人员Id',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '工单状态，1：初始化，2：进行中，3：结束',
  `score` int(11) DEFAULT NULL COMMENT '工单评分',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客服工单表'
