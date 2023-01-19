package org.geekbang.projects.cs.search.lucene.index;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.geekbang.projects.cs.search.lucene.entity.Book;
import org.geekbang.projects.cs.search.lucene.util.IndexUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class BookIndex {

    private String indexPath = "C:/lucene/index";

    private IndexWriter writer;

    public BookIndex() {
        try {
            File file = new File(indexPath);
            if(!file.exists()){
                file.mkdir();
            }
            this.writer = IndexUtil.getIndexWriter(indexPath,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void indexDoc(List<Book> baikes) throws Exception {
        for(Book baike : baikes) {
            Document doc = new Document();
            Field id = new Field("id", baike.getId()+"", TextField.TYPE_STORED);
            Field title = new Field("title", baike.getTitle(), TextField.TYPE_STORED);
            Field summary = new Field("summary", baike.getSummary(), TextField.TYPE_STORED);
            //添加到Document中
            doc.add(id);
            doc.add(title);
            doc.add(summary);
            if (writer.getConfig().getOpenMode() == IndexWriterConfig.OpenMode.CREATE){
                writer.addDocument(doc);
            }else{
                writer.updateDocument(new Term("id", baike.getId()+""), doc);
            }
        }

        writer.commit();
        writer.close();
    }
}