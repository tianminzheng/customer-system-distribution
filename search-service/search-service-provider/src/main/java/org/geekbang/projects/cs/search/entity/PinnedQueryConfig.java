package org.geekbang.projects.cs.search.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@ToString
@Data
@Accessors(chain = true)
@TableName(value = "pinned_query_config")
public class PinnedQueryConfig implements Serializable {
	
    @TableId(type = IdType.AUTO)
    private Long id;

    private String platform;

    private String businessType;

    private String subjectWord;
    
    private Date startTime;

    private Date endTime;

    private Integer status;

    private String contentIds;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}