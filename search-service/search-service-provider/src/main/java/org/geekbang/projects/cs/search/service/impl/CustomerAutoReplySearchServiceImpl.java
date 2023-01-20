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
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.geekbang.projects.cs.infrastructure.page.PageObject;
import org.geekbang.projects.cs.infrastructure.vo.Result;
import org.geekbang.projects.cs.search.config.EsIndexProerties;
import org.geekbang.projects.cs.search.controller.vo.SearchParamReq;
import org.geekbang.projects.cs.search.entity.CustomerAutoReply;
import org.geekbang.projects.cs.search.entity.PinnedQueryConfig;
import org.geekbang.projects.cs.search.service.CustomerAutoReplySearchService;
import org.geekbang.projects.cs.search.service.PinnedQueryConfigService;
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

    @Autowired
    PinnedQueryConfigService pinnedQueryConfigService;

    @Override
    public Result<PageObject<CustomerAutoReply>> searchCustomerAutoReplies(SearchParamReq searchParamReq) throws IOException {

        fillPinnedQuery(searchParamReq);

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

    private void fillPinnedQuery(SearchParamReq searchParamReq) {
        //从数据库中获取置顶配置
        PinnedQueryConfig pinnedQueryConfig;
        try {
            pinnedQueryConfig = pinnedQueryConfigService.findActivePinnedQueryConfigBySubjectWord(searchParamReq.getKeyWord(),1); //1代表是客服业务类型
            if(!Objects.isNull(pinnedQueryConfig)) {
                searchParamReq.setPinnedContentIds(Arrays.asList(pinnedQueryConfig.getContentIds().split("\\|")));
            }
        } catch (Exception e) {
            searchParamReq.setPinnedContentIds(null);
            log.error("从数据库中获取置顶配置失败:{}", e.getMessage(), e);
        }
    }

    private void createPinnedQuery(SearchParamReq searchParamReq, SearchSourceBuilder sourceBuilder) {
        //CustomerAutoReply搜索置顶
        List<String> customerAutoReplyIds = searchParamReq.getPinnedContentIds();
        if(customerAutoReplyIds != null) {
            String customerAutoReplyString = buildQueryString(customerAutoReplyIds);

            Script customerAutoReplyPinnedScript = new Script(
                    "List customerAutoReplyList = " + customerAutoReplyString +";"
                            + "Long customerAutoReplyId = doc['id'].value;"
                            + "if(customerAutoReplyList.contains(customerAutoReplyId)) { return 0; }"
                            + "else {return 1;}"
            );

            ScriptSortBuilder customerAutoReplyPinnedSort = new ScriptSortBuilder(customerAutoReplyPinnedScript, ScriptSortBuilder.ScriptSortType.NUMBER)
                    .order(SortOrder.ASC);
            sourceBuilder.sort(customerAutoReplyPinnedSort);
        }
    }

    private String buildQueryString(List<String> customerAutoReplyIds) {
        List<String> targetIds = new ArrayList<>();
        for (String id : customerAutoReplyIds) {
            String formatId = id + "L";//需要添加L后缀表明是Long类型
            targetIds.add(formatId);
        }
        String[] customerAutoReplyList = targetIds.toArray(new String[0]);
        return Arrays.toString(customerAutoReplyList);
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
        }

        //添加置顶搜索
        createPinnedQuery(searchParamReq, sourceBuilder);

        sourceBuilder.query(boolQuery);
        sourceBuilder.from((searchParamReq.getPageNum() - 1) * searchParamReq.getPageSize());
        sourceBuilder.size(searchParamReq.getPageSize());

        SearchRequest searchRequest = new SearchRequest(new String[]{esIndexProerties.getCustomerAutoReplyIndex()}, sourceBuilder);

        log.info("searchRequest---->" + searchRequest);
        log.info("searchSourceBuilder---->" + sourceBuilder);

        return searchRequest;
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
