package crawler;

import hollywood.crawler.PictureUrlCrawler;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by lingda on 2015/11/30.
 */
public class PictureUrlCrawlerTest {

    @Test
    public void getMoviePosterUrl() throws IOException {
        System.out.println(new PictureUrlCrawler().getMoviePosterUrl("https://www.themoviedb.org/movie/10997"));
    }
}
