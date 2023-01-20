package org.geekbang.projects.cs.search.controller;

import org.geekbang.projects.cs.infrastructure.page.PageObject;
import org.geekbang.projects.cs.infrastructure.vo.Result;
import org.geekbang.projects.cs.search.controller.vo.SearchParamReq;
import org.geekbang.projects.cs.search.entity.CustomerAutoReply;
import org.geekbang.projects.cs.search.service.CustomerAutoReplySearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/customerAutoReply")
public class CustomerAutoReplySearchController {

    @Autowired
    private CustomerAutoReplySearchService customerAutoReplySearchService;

    @PostMapping("/search")
    public Result<PageObject<CustomerAutoReply>> search(@RequestBody SearchParamReq searchParamReq) throws IOException {
        Result<PageObject<CustomerAutoReply>> result = customerAutoReplySearchService.searchCustomerAutoReplies(searchParamReq);
        return result;
    }
}

