package org.geekbang.projects.cs.message.entity;

//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Data
//@TableName("im_business_type")
public class ImBusinessType implements Serializable {

    private static final long serialVersionUID = 1L;

//    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String businessTypeCode;
    private String businessTypeName;
}
