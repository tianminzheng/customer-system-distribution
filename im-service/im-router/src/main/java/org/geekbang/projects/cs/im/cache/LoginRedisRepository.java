package org.geekbang.projects.cs.im.cache;

import org.geekbang.projects.cs.im.dto.IMLoginRequest;

import java.util.List;

public interface LoginRedisRepository {

    void saveLogin(IMLoginRequest imLoginRequest);

    void updateLogin(IMLoginRequest imLoginRequest);

    void deleteLogin(String userId);

    IMLoginRequest findLoginByUserId(String userId);

    Boolean isLogin(String userId);

    List<IMLoginRequest> getAllIMLoginRequests();
}
