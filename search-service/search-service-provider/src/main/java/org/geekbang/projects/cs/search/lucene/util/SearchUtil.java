package org.geekbang.projects.cs.search.lucene.util;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;

public class SearchUtil {

    public static IndexSearcher getIndexSearcher(String parentPath, ExecutorService service) throws IOException {

        IndexReader reader = null;
        try {
            reader = DirectoryReader.open(FSDirectory.open(Paths.get(parentPath, new String[0])));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new IndexSearcher(reader, service);
    }

    public static TopDocs getScoreDocs(IndexSearcher searcher,Query query) throws IOException{
        TopDocs result = searcher.search(query, 1000);
        return result;
    }

    public static TopDocs getScoreDocsByPerPage(int page,int perPage,IndexSearcher searcher,Query query) throws IOException{
        TopDocs result = null;
        if(query == null){
            System.out.println(" Query is null return null ");
            return null;
        }
        ScoreDoc before = null;
        if(page != 1){
            TopDocs docsBefore = searcher.search(query, (page-1)*perPage);
            ScoreDoc[] scoreDocs = docsBefore.scoreDocs;
            if(scoreDocs.length > 0){
                before = scoreDocs[scoreDocs.length - 1];
            }
        }
        result = searcher.searchAfter(before, query, perPage);
        return result;
    }

}