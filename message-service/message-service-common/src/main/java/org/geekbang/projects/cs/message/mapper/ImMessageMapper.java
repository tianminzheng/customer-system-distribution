package org.geekbang.projects.cs.message.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.geekbang.projects.cs.message.entity.ImMessage;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface ImMessageMapper{
    void addImMessage(ImMessage entity) throws SQLException;

    List<ImMessage> findImMessages() throws SQLException;

    List<ImMessage> findImMessagesByUser(@Param("toUserId") Long toUserId);
}
