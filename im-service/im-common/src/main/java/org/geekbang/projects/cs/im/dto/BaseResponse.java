package org.geekbang.projects.cs.im.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class BaseResponse {
    /**
     * 返回码,0000表示成功
     */
    private String code = "0000";
    /**
     * 返回消息
     */
    private String msg;

    /**
     * 判断是否操作成功，0000表示成功
     */
    public boolean success(){
        return "0000".equals(code);
    }

}
