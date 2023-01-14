package org.geekbang.projects.cs.im.service;

import org.geekbang.projects.cs.im.dto.ChatResponse;
import org.geekbang.projects.cs.im.dto.P2PChatRequest;

public interface ChatService {

    ChatResponse p2pChat(P2PChatRequest request);
}
