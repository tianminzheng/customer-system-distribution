package org.geekbang.projects.cs.message.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.geekbang.projects.cs.message.entity.ImBusinessType;

@Mapper
public interface ImBusinessTypeMapper{

    ImBusinessType findBusinessTypeByCode(@Param("businessTypeCode") String businessTypeCode);
}
