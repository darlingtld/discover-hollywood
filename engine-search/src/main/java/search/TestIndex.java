package search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by lingda on 2015/11/28.
 */
public class TestIndex {

    public static void main(String[] args) throws IOException {
        String[] ids = {"1", "2", "3"};
        String[] names = {"zhangsan", "lisi", "wangwu"};
        String[] address = {"shanghai", "beijing", "guangzhou"};

        Analyzer analyzer = new StandardAnalyzer();
        String indexDir = "d:/luceneindex";
        Directory dir;
        dir = FSDirectory.open(Paths.get(indexDir));
        //true: 表示创建或覆盖当前的索引  false:表示对当前索引进行追加
        //Default values is 128
        IndexWriterConfig conf = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(dir, conf);
        for (int i = 0; i < ids.length; i++) {
            Document doc = new Document();
            doc.add(new Field("id", ids[i], Field.Store.YES, Field.Index.ANALYZED));
            doc.add(new Field("name", names[i], Field.Store.YES, Field.Index.NO));
            doc.add(new Field("address", address[i], Field.Store.YES, Field.Index.ANALYZED));
            writer.addDocument(doc);
        }
        writer.close();
    }


}
