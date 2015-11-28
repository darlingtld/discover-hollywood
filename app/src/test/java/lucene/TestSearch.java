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
public class TestSearch {

    @Autowired
    private LuceneSearcher luceneSearcher;

    @Test
    public void search() {
        try {
            Long startTime = System.currentTimeMillis();
            List<Movie> result = luceneSearcher.getMovieResult("s");
            int i = 0;
            for (Movie bean : result) {
                if (i == 10) break;
                System.out.println(bean);
                i++;
            }
            System.out.println("searchBean.result.size : " + result.size());

            Long endTime = System.currentTimeMillis();
            System.out.println("查询所花费的时间为：" + (endTime - startTime) / 1000);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
