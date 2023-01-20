package org.geekbang.projects.cs.search.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.geekbang.projects.cs.infrastructure.page.PageObject;
import org.geekbang.projects.cs.infrastructure.vo.Result;
import org.geekbang.projects.cs.search.config.EsIndexProerties;
import org.geekbang.projects.cs.search.controller.vo.SearchParamReq;
import org.geekbang.projects.cs.search.entity.CustomerAutoReply;
import org.geekbang.projects.cs.search.service.CustomerAutoReplySearchService;
import org.geekbang.projects.cs.search.utils.HighlightUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class CustomerAutoReplySearchServiceImpl implements CustomerAutoReplySearchService {

    @Autowired
    RestHighLevelClient esClient;

    @Autowired
    EsIndexProerties esIndexProerties;

    @Override
    public Result<PageObject<CustomerAutoReply>> searchCustomerAutoReplies(SearchParamReq searchParamReq) throws IOException {

        //1.创建搜索请求
        SearchRequest searchRequest = createSearchRequest(searchParamReq);

        //2.执行搜索
        SearchResponse response;
        try {
            response = esClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("查询ES错误,{}", e.getMessage());
            return Result.error("查询ES错误");
        }

        log.info("返回搜索结果:{}", response.toString());


        //3.解析搜索结果
        SearchHits hits = response.getHits();
        List<CustomerAutoReply> customerAutoReplies = new ArrayList<>();
        for (SearchHit hit : hits) {
            Map<String, Object> result = hit.getSourceAsMap();
            CustomerAutoReply customerAutoReply = convertCustomerAutoReply(result);
            customerAutoReplies.add(customerAutoReply);
        }

        PageObject<CustomerAutoReply> page = new PageObject();
        page.buildPage(customerAutoReplies,
                hits.getTotalHits().value,
                Long.valueOf(searchParamReq.getPageNum().toString()),
                Long.valueOf(searchParamReq.getPageSize().toString()));

        return Result.success(page);
    }

    private SearchRequest createSearchRequest(SearchParamReq searchParamReq) {

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        //执行项搜索
        boolQuery.must(QueryBuilders.termQuery("is_deleted", searchParamReq.getIsDeleted()));

        String keyWord = searchParamReq.getKeyWord();
        if (StringUtils.hasText(keyWord)) {
            //执行多字段组合全文搜索
            boolQuery.must(QueryBuilders.multiMatchQuery(keyWord, "word", "content")
                    .field("word", 1f)
                    .field("content", 0.25f)
                    .type(MultiMatchQueryBuilder.Type.MOST_FIELDS)
                    .operator(Operator.AND));

            //嵌入结果高亮效果
            embedHighLight(searchParamReq, sourceBuilder);
        }

        sourceBuilder.query(boolQuery);
        sourceBuilder.from((searchParamReq.getPageNum() - 1) * searchParamReq.getPageSize());
        sourceBuilder.size(searchParamReq.getPageSize());

        SearchRequest searchRequest = new SearchRequest(new String[]{esIndexProerties.getCustomerAutoReplyIndex()}, sourceBuilder);

        log.info("searchRequest---->" + searchRequest);
        log.info("searchSourceBuilder---->" + sourceBuilder);

        return searchRequest;
    }

    private void embedHighLight(SearchParamReq searchParamReq, SearchSourceBuilder sourceBuilder) {
        List<String> fields = Arrays.asList("word", "content");
        if(StringUtils.hasText(searchParamReq.getPreTags())&&StringUtils.hasText(searchParamReq.getPostTags())){
            HighlightBuilder highlightBuilder = HighlightUtils.highlightByField(fields, searchParamReq.getPreTags(),searchParamReq.getPostTags());
            sourceBuilder.highlighter(highlightBuilder);
        }
    }

    private CustomerAutoReply convertCustomerAutoReply(Map<String, Object> result) {
        Long id = Long.parseLong(result.get("id").toString());
        String word = result.get("word").toString();
        String content = result.get("content").toString();
        Integer isDeleted = Integer.parseInt(result.get("is_deleted").toString());

        CustomerAutoReply customerAutoReply = new CustomerAutoReply()
                .setId(id)
                .setWord(word)
                .setContent(content)
                .setIsDeleted(isDeleted);
        return customerAutoReply;
    }
}
