package hollywood;

import hollywood.dao.MovieDao;
import hollywood.dao.TagDao;
import hollywood.pojo.Movie;
import hollywood.pojo.Tag;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by lingda on 2015/11/28.
 * Generate lucene index on movies.
 * It is quartz job. It shall append indices to the existing ones based on the lastId
 */
@Service
public class IndexGenerator {

    private static final String searchDir = PropertyHolder.LUCENE_INDEX_DIR;
    private static final int maxBufferedDocs = PropertyHolder.MAX_BUFFERED_DOCS;
    private static final Logger logger = LoggerFactory.getLogger(IndexGenerator.class);

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private TagDao tagDao;

    @Transactional
    public int createMovieIndex(int lastId) {
        Directory directory;
        IndexWriter indexWriter;
        try {
            File indexFile = new File(searchDir);
            if (!indexFile.exists()) {
                indexFile.mkdir();
            }
            directory = FSDirectory.open(Paths.get(searchDir));
            Analyzer analyzer = new StandardAnalyzer();
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
//                    build index on three fields (movieId, title, genres)
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
            return movieList.get(movieList.size() - 1).getMovieId();
        } catch (Exception e) {
            logger.error(e.getMessage());
//            will return lastId.  The next trigger of this job shall retry the above process
        }
        return lastId;
    }

    @Transactional
    public int createTagIndex(int lastId) {
        Directory directory;
        IndexWriter indexWriter;
        try {
            File indexFile = new File(searchDir);
            if (!indexFile.exists()) {
                indexFile.mkdir();
            }
            directory = FSDirectory.open(Paths.get(searchDir));
            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            indexWriter = new IndexWriter(directory, iwc);
            Document doc;
            int start = lastId;
            List<Tag> tagList;
            do {
                tagList = tagDao.getByPagination(start, maxBufferedDocs);
                for (Tag tag : tagList) {
                    doc = new Document();
//                    build index on two fields (movieId, tag)
                    Field movieId = new Field("movieId", String.valueOf(tag.getMovieId()), Field.Store.YES, Field.Index.NOT_ANALYZED, Field.TermVector.NO);
                    Field movieTag = new Field("tag", tag.getTag(), Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.NO);
                    doc.add(movieId);
                    doc.add(movieTag);
                    indexWriter.addDocument(doc);
                }
                start += maxBufferedDocs;

            } while (tagList.size() == maxBufferedDocs);
            indexWriter.close();
            return tagList.get(tagList.size() - 1).getId();
        } catch (Exception e) {
            logger.error(e.getMessage());
//            will return lastId.  The next trigger of this job shall retry the above process
        }
        return lastId;
    }
}
