package org.geekbang.projects.cs.message.controller;

import org.geekbang.projects.cs.message.converter.ImMessageConverter;
import org.geekbang.projects.cs.message.domain.ImMessageDTO;
import org.geekbang.projects.cs.message.entity.ImMessage;
import org.geekbang.projects.cs.message.service.ImMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class ImMessageController {

    @Autowired
    private ImMessageService imMessageService;

    @PostMapping("/")
    public Long saveImMessage(@RequestBody ImMessageDTO imMessageDTO)  throws SQLException {

        ImMessage imMessage = ImMessageConverter.INSTANCE.convert(imMessageDTO);
        imMessageService.saveImMessage(imMessage);

        return imMessage.getId();
    }

    @GetMapping()
    public List<ImMessageDTO> findImMessages() throws SQLException {

        List<ImMessage> imMessages = imMessageService.findImMessages();
        return ImMessageConverter.INSTANCE.convertDTOs(imMessages);
    }

    @GetMapping("/{userId}")
    public List<ImMessageDTO> findImMessagesByUserId(@PathVariable("userId") Long userId) throws SQLException {

        List<ImMessage> imMessages = imMessageService.findImMessagesByUserId(userId);
        return ImMessageConverter.INSTANCE.convertDTOs(imMessages);
    }
}
