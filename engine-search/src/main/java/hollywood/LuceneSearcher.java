package hollywood;

import hollywood.pojo.Movie;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lingda on 2015/11/28.
 */
@Component
public class LuceneSearcher {

    private static IndexSearcher searcher = null;
    private String searchDir = PropertyHolder.LUCENE_INDEX_DIR;
    private int maxHits = PropertyHolder.LUCENE_MAX_HITS;


    public List<Movie> getMovieResult(String queryStr) throws Exception {

        TopDocs topDocs = search(queryStr);

        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        return addHits2List(scoreDocs);
    }

    private TopDocs search(String queryStr) throws Exception {

        if (searcher == null) {
            searcher = new IndexSearcher(DirectoryReader.open(FSDirectory.open(Paths.get(searchDir))));
        }
        QueryParser parser = new QueryParser("title", new StandardAnalyzer());
        Query query = parser.parse(queryStr.replace("\"", ""));

        TopDocs topDocs = searcher.search(query, maxHits);

        return topDocs;
    }

    private List<Movie> addHits2List(ScoreDoc[] scoreDocs) throws Exception {

        List<Movie> listBean = new ArrayList<>();
        Movie bean;
        for (int i = 0; i < scoreDocs.length; i++) {
            int docId = scoreDocs[i].doc;
            Document doc = searcher.doc(docId);
            bean = new Movie();
            bean.setTitle(doc.get("title"));
            bean.setGenres(doc.get("genres"));
            bean.setMovieId(Integer.parseInt(doc.get("movieId")));
            listBean.add(bean);
        }
        return listBean;
    }
}
