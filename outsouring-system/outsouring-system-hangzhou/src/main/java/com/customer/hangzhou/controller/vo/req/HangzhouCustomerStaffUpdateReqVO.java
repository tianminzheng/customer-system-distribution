package com.customer.hangzhou.controller.vo.req;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class HangzhouCustomerStaffUpdateReqVO {

    private Long id;
    private String nickname;
    private String avatar;
    private String gender;
    private String goodAt;
    private String remark;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isDeleted;
}
