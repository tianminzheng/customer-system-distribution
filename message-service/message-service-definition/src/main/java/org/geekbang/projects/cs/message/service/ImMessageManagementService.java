package org.geekbang.projects.cs.message.service;

import org.geekbang.projects.cs.message.domain.ImMessageDTO;

import java.util.List;

public interface ImMessageManagementService {

    Long saveImMessage(ImMessageDTO imMessageDTO);

    List<ImMessageDTO> findImMessages();

    List<ImMessageDTO> findImMessagesByUserId(Long userId);
}
