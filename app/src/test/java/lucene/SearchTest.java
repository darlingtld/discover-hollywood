package lucene;

import hollywood.LuceneSearcher;
import hollywood.pojo.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by lingda on 2015/11/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:mvc-dispatcher-servlet.xml"})
public class SearchTest {

    @Autowired
    private LuceneSearcher luceneSearcher;

    @Test
    public void search() {
        try {
            Long startTime = System.currentTimeMillis();
            List<Movie> result = luceneSearcher.searchMoviesByTitle("solo", 6);
            result.forEach(System.out::println);
            System.out.println("search result size : " + result.size());
            Long endTime = System.currentTimeMillis();
            System.out.println("time spent：" + (endTime - startTime) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
