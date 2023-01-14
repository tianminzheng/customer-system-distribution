package org.geekbang.projects.cs.integration.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class OutsourcingSystemDTO implements Serializable {
    /**
     * 系统名称
     */
    private String systemName;

    /**
     * 系统编码
     */
    private String systemCode;

    /**
     * 系统描述
     */
    private String description;

    /**
     * 系统访问URL
     */
    private String systemUrl;
}
