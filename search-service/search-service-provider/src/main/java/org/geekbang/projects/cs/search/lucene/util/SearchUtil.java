package org.geekbang.projects.cs.search.lucene.util;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
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
}