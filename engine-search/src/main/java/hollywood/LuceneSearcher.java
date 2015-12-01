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

    private static final String searchDir = PropertyHolder.LUCENE_INDEX_DIR;
    private static final int maxHits = PropertyHolder.LUCENE_MAX_HITS;
    private static IndexSearcher searcher = null;

    /**
     * search movies by titles
     *
     * @param queryStr
     * @param limit
     * @return
     * @throws Exception
     */
    public List<Movie> searchMoviesByTitle(String queryStr, int limit) throws Exception {
        TopDocs topDocs = search("title", queryStr);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        return addHits2List(scoreDocs, limit);
    }

    /**
     * search movies by genres
     *
     * @param genres
     * @param limit
     * @return
     * @throws Exception
     */
    public List<Movie> searchMoviesByGenres(String genres, int limit) throws Exception {
        TopDocs topDocs = search("genres", genres);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        return addHits2List(scoreDocs, limit);
    }

    /**
     * get top hit documents based on the given query
     *
     * @param field
     * @param queryStr
     * @return
     * @throws Exception
     */
    private TopDocs search(String field, String queryStr) throws Exception {
        if (searcher == null) {
            searcher = new IndexSearcher(DirectoryReader.open(FSDirectory.open(Paths.get(searchDir))));
        }
        QueryParser parser = new QueryParser(field, new StandardAnalyzer());
//        " may cause some unexpected exception
        Query query = parser.parse(queryStr.replace("\"", ""));

        TopDocs topDocs = searcher.search(query, maxHits);

        return topDocs;
    }

    /**
     * add scored documents to movie list
     *
     * @param scoreDocs
     * @param limit
     * @return
     * @throws Exception
     */
    private List<Movie> addHits2List(ScoreDoc[] scoreDocs, int limit) throws Exception {
        List<Movie> listBean = new ArrayList<>();
        Movie bean;
        for (int i = 0; i < Math.min(scoreDocs.length, limit); i++) {
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


    /**
     * search movie by movie tags
     *
     * @param tag
     * @param count
     * @return
     */
    public List<Movie> searchMoviesByTag(String tag, int count) throws Exception {
        TopDocs topDocs = search("tag", tag);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        return addHits2List(scoreDocs, count);
    }
}
