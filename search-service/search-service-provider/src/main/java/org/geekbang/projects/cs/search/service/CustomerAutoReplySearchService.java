package org.geekbang.projects.cs.search.service;


import org.geekbang.projects.cs.infrastructure.page.PageObject;
import org.geekbang.projects.cs.infrastructure.vo.Result;
import org.geekbang.projects.cs.search.controller.vo.SearchParamReq;
import org.geekbang.projects.cs.search.entity.CustomerAutoReply;

import java.io.IOException;
import java.util.List;

public interface CustomerAutoReplySearchService {

    Result<PageObject<CustomerAutoReply>> searchCustomerAutoReplies(SearchParamReq searchParamReq) throws IOException;
}
