package org.geekbang.projects.cs.search.lucene.service;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.geekbang.projects.cs.search.lucene.entity.Book;
import org.geekbang.projects.cs.search.lucene.index.BookIndex;
import org.geekbang.projects.cs.search.lucene.util.SearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class SearchService {

    private String indexPath = "C:/lucene/index";

    @Autowired
    private BookIndex bookIndex;

    public void write(List<Book> books) throws Exception {

        bookIndex.indexDoc(books);
    }

    //搜索
    public List<Map> search(String value) throws Exception {

        List<Map> list = new ArrayList<Map>();
        ExecutorService service = Executors.newCachedThreadPool();
        //定义分词器
        Analyzer analyzer = new WhitespaceAnalyzer();
        try {
            IndexSearcher searcher = SearchUtil.getIndexSearcher(indexPath, service);
            String[] fields = {"title", "summary"};
            // 构造Query对象
            MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, analyzer);
            Query query = parser.parse(value);

            //执行搜索并获取结果
            TopDocs results = searcher.search(query, 1000);
            ScoreDoc[] hits = results.scoreDocs;

            //遍历结果并输出
            for (int i = 0; i < hits.length; i++) {
                Document hitDoc = searcher.doc(hits[i].doc);
                Map map = new HashMap();
                map.put("id", hitDoc.get("id"));

                //获取到summary
                String summary = hitDoc.get("summary");
                map.put("summary", summary);

                //获取到title
                String title = hitDoc.get("title");
                map.put("title", title);

                list.add(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            service.shutdownNow();
        }
        return list;
    }
}
