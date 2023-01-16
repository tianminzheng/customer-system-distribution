package org.geekbang.projects.cs.servicebus.transformer.hangzhou;

import com.alibaba.fastjson.JSON;
import org.geekbang.projects.cs.integration.domain.PlatformCustomerStaff;
import org.geekbang.projects.cs.integration.domain.enums.Gender;
import org.geekbang.projects.cs.integration.domain.enums.Status;
import org.geekbang.projects.cs.servicebus.router.hangzhou.dto.HangzhouCustomerStaff;
import org.geekbang.projects.cs.servicebus.transformer.CustomerStaffTransformer;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Component
public class HangzhouCustomerStaffTransformer implements CustomerStaffTransformer<HangzhouCustomerStaff> {

    @Override
    public List<PlatformCustomerStaff> transformCustomerStaffs(List<HangzhouCustomerStaff> hangzhouCustomerStaffs) {

        List<PlatformCustomerStaff> customerStaffs = new ArrayList<>();

        //把LinkedHashMap转换为List<HangzhouCustomerStaff>
        String string = JSON.toJSONString(hangzhouCustomerStaffs);
        List<HangzhouCustomerStaff> list = JSON.parseArray(string, HangzhouCustomerStaff.class);

        for(HangzhouCustomerStaff hangzhouCustomerStaff : list) {
            PlatformCustomerStaff customerStaff = new PlatformCustomerStaff();

            //填充StaffName
            customerStaff.setStaffName(hangzhouCustomerStaff.getNickname());
            customerStaff.setNickname(hangzhouCustomerStaff.getNickname());
            customerStaff.setPhone(hangzhouCustomerStaff.getPhone());
            customerStaff.setRemark(hangzhouCustomerStaff.getRemark());
            customerStaff.setGoodAt(hangzhouCustomerStaff.getGoodAt());
            customerStaff.setAvatar(hangzhouCustomerStaff.getAvatar());

            //转换性别
            if(hangzhouCustomerStaff.getGender() != null) {
                customerStaff.setGender(Gender.valueOf(hangzhouCustomerStaff.getGender()));
            }

            //转换时间
            if(hangzhouCustomerStaff.getCreateTime() != null) {
                ZoneId zone = ZoneId.systemDefault();
                Instant createdTimeInstance = hangzhouCustomerStaff.getCreateTime().toInstant();
                LocalDateTime createdTimeLocalDateTime = LocalDateTime.ofInstant(createdTimeInstance, zone);
                customerStaff.setCreateTime(createdTimeLocalDateTime);
            }

            //初始化AccountId和Status
            customerStaff.setAccountId(-1L);
            customerStaff.setStatus(Status.OFFLINE);

            customerStaffs.add(customerStaff);
        }

        return customerStaffs;
    }
}
