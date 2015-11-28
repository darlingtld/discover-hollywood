//package search;
//
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.index.IndexReader;
//import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
//import org.apache.lucene.search.*;
//
//import java.util.Date;
//
///**
// * Created by lingda on 2015/11/28.
// */
//public class TestSearch {
//
//    public static void main(String[] args){
//        String indexPath = "d:/luceneindex";
//        LuceneSearcher search = new LuceneSearcher();
//        long startTime = new Date().getTime();
//
//        //下面的是进行p_content和p_name 范围内进行搜索.
//        String[] keywords = new String[]{"p_content","p_name"};//要检索的字段
//        /** 这里需要注意的就是BooleanClause.Occur[]数组,它表示多个条件之间的关系,
//         * BooleanClause.Occur.MUST表示and,
//         * BooleanClause.Occur.MUST_NOT表示not,
//         * BooleanClause.Occur.SHOULD表示or.
//         * */
//        BooleanClause.Occur[] clauses = { BooleanClause.Occur.SHOULD,BooleanClause.Occur.SHOULD};//对应要检索的字段的逻辑（与、或）
//        Analyzer analyzer = new StandardAnalyzer();
//        //用MultiFieldQueryParser得到query对象
//        Query query = MultiFieldQueryParser.parse(keyword, keywords, clauses, analyzer);//parser.parse(query);
//        Filter filter = null;//过滤
//        //开始匹配
//        TopDocs topDocs = search.search(query, filter, 1000);
//        System.out.println("共匹配到："+topDocs.totalHits+"个.");
//
//        for(ScoreDoc scorceDoc : topDocs.scoreDocs){
//            Document doc = search.doc(scorceDoc.doc);
////            System.out.println(scorceDoc.doc+"---"+doc);//便于学习，可以打印出来看看。
//            Product product = new Product();
//            product.setP_id(Integer.parseInt(doc.get("p_id")));
//            product.setP_name(doc.get("p_name"));
//            product.setP_price(doc.get("p_price"));
////            product.setP_content(doc.get("p_content"));//不使用高亮
//            product.setP_content(this.getHighLight(doc, analyzer, query, "p_content"));//使用高亮
//            productList.add(product);
//        }
//        search.close();
//        long endTime = new Date().getTime();
//        System.out.println("检索耗时： " + (endTime - startTime)+ "毫秒!");
//        return productList;
//    }
//}
