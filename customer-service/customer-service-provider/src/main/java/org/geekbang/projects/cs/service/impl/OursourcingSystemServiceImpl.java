package org.geekbang.projects.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geekbang.projects.cs.entity.tenant.OutsourcingSystem;
import org.geekbang.projects.cs.infrastructure.page.PageObject;
import org.geekbang.projects.cs.mapper.OutsourcingSystemMapper;
import org.geekbang.projects.cs.service.IOutsourcingSystemService;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableCaching
@CacheConfig(cacheNames = "oursourcing-system-object")
public class OursourcingSystemServiceImpl extends ServiceImpl<OutsourcingSystemMapper, OutsourcingSystem> implements IOutsourcingSystemService {

    @Override
    @Cacheable(value = "pagedObject", key = "#root.targetClass + '_' + #p0 + '_' + #p1")
    public PageObject<OutsourcingSystem> findPagedOutsourcingSystems(Long pageSize, Long pageIndex) {

        IPage<OutsourcingSystem> pagedResult = baseMapper.findPagedOutsourcingSystems(pageSize, pageIndex);

        PageObject<OutsourcingSystem> pagedObject = new PageObject<>();
        pagedObject.buildPage(pagedResult.getRecords(), pagedResult.getTotal(), pagedResult.getCurrent(), pagedResult.getSize());

        return pagedObject;
    }

    @Override
    public List<OutsourcingSystem> findAllOutsourcingSystems() {
        LambdaQueryWrapper<OutsourcingSystem> queryWrapper = new LambdaQueryWrapper<>();
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    @Cacheable(key = "#root.targetClass + '_' + #systemId")
    public OutsourcingSystem findOutsourcingSystemById(Long systemId) {

        return baseMapper.selectById(systemId);
    }

    @Override
    @CachePut(key = "#root.targetClass + '_' + #outsourcingSystem.id")
    public Boolean addOutsourcingSystem(OutsourcingSystem outsourcingSystem) {

        return this.save(outsourcingSystem);
    }

    @Override
    @CachePut(key = "#root.targetClass + '_' + #outsourcingSystem.id")
    public Boolean updateOutsourcingSystem(OutsourcingSystem outsourcingSystem) {

        return this.updateById(outsourcingSystem);
    }

    @Override
    @CacheEvict(key = "#root.targetClass + '_' + #systemId")
    public Boolean deleteOutsourcingSystemById(Long systemId) {

        return this.removeById(systemId);
    }
}
