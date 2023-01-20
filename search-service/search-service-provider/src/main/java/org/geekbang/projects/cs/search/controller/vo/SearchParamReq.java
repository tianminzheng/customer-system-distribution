package org.geekbang.projects.cs.search.controller.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SearchParamReq {

    private String keyWord;

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private Integer isDeleted;

    /**
     * 高亮前缀
     */
    private String preTags;

    /**
     * 高亮后缀
     */
    private String postTags;

}
