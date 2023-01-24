package org.geekbang.projects.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geekbang.projects.cs.entity.staff.CustomerStaff;
import org.geekbang.projects.cs.entity.tenant.OutsourcingSystem;
import org.geekbang.projects.cs.event.CustomerStaffChangedEventProducer;
import org.geekbang.projects.cs.event.CustomerStaffChangedEventWithTagProducer;
import org.geekbang.projects.cs.infrastructure.exception.BizException;
import org.geekbang.projects.cs.infrastructure.page.PageObject;
import org.geekbang.projects.cs.integration.CustomerStaffIntegrationClient;
import org.geekbang.projects.cs.mapper.CustomerStaffMapper;
import org.geekbang.projects.cs.service.ICustomerStaffService;
import org.geekbang.projects.cs.service.IOutsourcingSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CustomerStaffServiceImpl extends ServiceImpl<CustomerStaffMapper, CustomerStaff> implements ICustomerStaffService {

    @Autowired
    IOutsourcingSystemService outsourcingSystemService;

    @Autowired
    CustomerStaffIntegrationClient customerStaffIntegrationClient;

    @Autowired
    CustomerStaffChangedEventProducer customerStaffChangedEventProducer;

    @Autowired
    CustomerStaffChangedEventWithTagProducer customerStaffChangedEventWithTagProducer;

    @Override
    public PageObject<CustomerStaff> findCustomerStaffs(Long pageSize, Long pageIndex) {

        return getCustomerStaffPageObject(null, pageSize, pageIndex);
    }

    @Override
    public List<CustomerStaff> findCustomerStaffs() {
        LambdaQueryWrapper<CustomerStaff> queryWrapper = new LambdaQueryWrapper<>();
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public PageObject<CustomerStaff> findCustomerStaffsByName(String staffName, Long pageSize, Long pageIndex) {
        return getCustomerStaffPageObject(staffName, pageSize, pageIndex);
    }

    private PageObject<CustomerStaff> getCustomerStaffPageObject(String staffName, Long pageSize, Long pageIndex) {
        LambdaQueryWrapper<CustomerStaff> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(CustomerStaff::getIsDeleted, false);

        if(!Objects.isNull(staffName)) {
            queryWrapper.like(CustomerStaff::getStaffName, staffName);
        }
        queryWrapper.orderByDesc(CustomerStaff::getCreateTime);

        IPage<CustomerStaff> page = new Page<>(pageIndex, pageSize);
        IPage<CustomerStaff> pagedResult = baseMapper.selectPage(page, queryWrapper);

        PageObject<CustomerStaff> pagedObject = new PageObject<CustomerStaff>();
        pagedObject.buildPage(pagedResult.getRecords(), pagedResult.getTotal(), pagedResult.getCurrent(), pagedResult.getSize());

        return pagedObject;
    }


    @Override
    public CustomerStaff findCustomerStaffById(Long staffId) {

        return baseMapper.selectById(staffId);
    }

    @Override
    public Boolean createCustomerStaff(CustomerStaff customerStaff) throws BizException {

        Boolean saved = this.save(customerStaff);

//        customerStaffChangedEventProducer.sendCustomerStaffChangedEvent(customerStaff, "CREATE");
        customerStaffChangedEventWithTagProducer.sendCustomerStaffChangedEvent(customerStaff, "CREATE");

        return saved;
    }

    @Override
    public Boolean updateCustomerStaff(CustomerStaff customerStaff) {

        Boolean updated = this.updateById(customerStaff);

//        customerStaffChangedEventProducer.sendCustomerStaffChangedEvent(customerStaff, "UPDATED");
        customerStaffChangedEventWithTagProducer.sendCustomerStaffChangedEvent(customerStaff, "UPDATED");

        return updated;
    }

    @Override
    public Boolean deleteCustomerStaffById(Long staffId) {

        //通过更新操作实现逻辑删除
//        CustomerStaff customerStaff = new CustomerStaff();
//        customerStaff.setId(staffId);
//        customerStaff.setIsDeleted(true);
//
//        return updateById(customerStaff);

        CustomerStaff customerStaff = this.findCustomerStaffById(staffId);

//        customerStaffChangedEventProducer.sendCustomerStaffChangedEvent(customerStaff, "DELETED");
        customerStaffChangedEventWithTagProducer.sendCustomerStaffChangedEvent(customerStaff, "DELETED");

        //通过逻辑删除为来进行逻辑删除
        return this.removeById(staffId);
    }

    @Override
    public void syncOutsourcingCustomerStaffsBySystemId(Long systemId) {

        //获取租户信息
        OutsourcingSystem outsourcingSystem = outsourcingSystemService.findOutsourcingSystemById(systemId);

        //调用远程Dubbo接口获取CustomerStaff列表
        List<CustomerStaff> customerStaffs = customerStaffIntegrationClient.getCustomerStaffs(outsourcingSystem);

        //保存客服信息
        saveOrUpdateBatch(customerStaffs);
    }
}
