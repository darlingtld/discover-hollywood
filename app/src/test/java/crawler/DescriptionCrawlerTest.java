package crawler;

import hollywood.crawler.DescriptionCrawler;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by lingda on 2015/11/30.
 */
public class DescriptionCrawlerTest {

    @Test
    public void getMovieDescription() throws IOException {
        System.out.println(new DescriptionCrawler().getMovieDescription("https://www.themoviedb.org/movie/10997"));
    }
}
