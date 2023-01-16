package org.geekbang.projects.cs.message.converter;

import org.geekbang.projects.cs.message.domain.ImMessageDTO;
import org.geekbang.projects.cs.message.entity.ImMessage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ImMessageConverter {

    ImMessageConverter INSTANCE = Mappers.getMapper(ImMessageConverter.class);

    //DTO->Entity
    ImMessage convert(ImMessageDTO dto);

    //Entity->DTO
    ImMessageDTO convertDTO(ImMessage entity);
    List<ImMessageDTO> convertDTOs(List<ImMessage> entities);
}
