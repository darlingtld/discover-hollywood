package hollywood;

import hollywood.dao.MovieDao;
import hollywood.pojo.Movie;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by lingda on 2015/11/28.
 */
@Service
public class IndexGenerator {

    private String searchDir = PropertyHolder.LUCENE_INDEX_DIR;

    private static File indexFile = null;

    private static Analyzer analyzer = null;

    private int maxBufferedDocs = 500;

    @Autowired
    private MovieDao movieDao;

    @Transactional
    public int createMovieIndex(int lastId) {
        Directory directory;
        IndexWriter indexWriter;
        try {
            indexFile = new File(searchDir);
            if (!indexFile.exists()) {
                indexFile.mkdir();
            }
            directory = FSDirectory.open(Paths.get(searchDir));
            analyzer = new StandardAnalyzer();
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            indexWriter = new IndexWriter(directory, iwc);
            Document doc;
            int start = lastId;
            List<Movie> movieList;
            do {
                movieList = movieDao.getByPagination(start, maxBufferedDocs);
                for (Movie movie : movieList) {
                    doc = new Document();
                    Field movieId = new Field("movieId", String.valueOf(movie.getMovieId()), Field.Store.YES, Field.Index.NOT_ANALYZED, Field.TermVector.NO);
                    Field movieTitle = new Field("title", movie.getTitle(), Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.NO);
                    Field movieGenres = new Field("genres", movie.getGenres(), Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.NO);
                    doc.add(movieId);
                    doc.add(movieTitle);
                    doc.add(movieGenres);
                    indexWriter.addDocument(doc);
                }
                start += maxBufferedDocs;

            } while (movieList.size() == maxBufferedDocs);
            indexWriter.close();
            return start + movieList.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastId;
    }
}
