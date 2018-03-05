package com.wyk.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class LuceneQueryParseTest{ 

	/**使用QuqeyParse查询*/
	@Test
	public void testQueryParse() throws ParseException, IOException {
	
		IndexSearcher searcher = getIndexSearcher();
		QueryParser parser = new QueryParser("filecontent", new IKAnalyzer());
		Query parse = parser.parse("notice");
		
		TopDocs docs = searcher.search(parse, 10);
		
		System.out.println("查询到的条数 ： " + docs.totalHits); 
		for(ScoreDoc scoreDoc : docs.scoreDocs) {
			Document document = searcher.doc(scoreDoc.doc);
			System.out.println("文件名称为： "+ document.get("filename"));
		}
		
	}
	
	@Test
	/**使用MultiFieldQueryParser指定多个默认搜索域*/
	public void testMultiFieldQueryParser() throws Exception{
		
		IndexSearcher indexSearcher = getIndexSearcher();
		
		String[] fields = {"filename", "filecontent"};
		//创建一个MulitFiledQueryParser对象
		MultiFieldQueryParser queryParser = new MultiFieldQueryParser(fields, new IKAnalyzer());
		Query query = queryParser.parse("solrconfig and http");
		System.out.println(query);
		//执行查询
		TopDocs docs = indexSearcher.search(query, 10);
		
		System.out.println("查询到的条数 ： " + docs.totalHits); 
		for(ScoreDoc scoreDoc : docs.scoreDocs) {
			Document document = indexSearcher.doc(scoreDoc.doc);
			System.out.println("文件名称为： "+ document.get("filename"));
		}
		
	}
	
	/**专门提供IndexSearcher对象*/
	public IndexSearcher getIndexSearcher() {
		IndexSearcher searcher = null;
		try {
			//索引存放的位置
			FSDirectory directory = FSDirectory.open(new File("D:\\project\\lucene_index"));
			
			DirectoryReader reader = DirectoryReader.open(directory);
			searcher = new IndexSearcher(reader);
			
		} catch (IOException e) { 
			//e.printStackTrace();
		}
		return searcher;
	}
}
