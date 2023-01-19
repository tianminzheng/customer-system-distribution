package org.geekbang.projects.cs.search.lucene.controller;

import org.geekbang.projects.cs.search.lucene.entity.Book;
import org.geekbang.projects.cs.search.lucene.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    //创建索引
    @GetMapping("/index")
    public String createIndex() throws Exception {
        // 拉取数据
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "hello", "this is hello book"));
        books.add(new Book(1, "blue sky", "this is blue sky book"));

        searchService.write(books);
        return "创建索引成功";
    }

    //搜索
    @GetMapping("search/{query}")
    public List<Map> getSearchText(@PathVariable String query) throws Exception {
        List<Map> mapList = searchService.search(query);
        return mapList;
    }
}
