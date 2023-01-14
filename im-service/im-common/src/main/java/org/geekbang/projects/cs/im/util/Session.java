package org.geekbang.projects.cs.im.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Session {
    private String userId;
    private String userName;
}
