package org.geekbang.projects.cs.search.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("customer_auto_reply")
public class CustomerAutoReply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关键词
     */
    private String word;

    /**
     * 自动回复内容
     */
    private String content;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 是否删除，1=删除,0=未删除
     */
    private Integer isDeleted;
}
