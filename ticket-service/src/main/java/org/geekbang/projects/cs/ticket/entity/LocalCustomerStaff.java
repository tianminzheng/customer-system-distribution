package org.geekbang.projects.cs.ticket.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 工单服务本地客服人员表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("local_customer_staff")
public class LocalCustomerStaff implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String staffName;
    private Long accountId;
    private String phone;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
