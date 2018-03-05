package com.wyk.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
/**
 * @author Administrator
 * 作用： 查询索引
 */
public class LuceneIndexReader {

	// 查询索引
	@Test
	public void searchIndex() throws IOException {
		
		//指定索引库存放的路径
		FSDirectory directory = FSDirectory.open(new File("D:\\project\\lucene_index"));
		
		//创建索引读取的对象
		IndexReader indexReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader); 
		
		//创建查询
		Query query = new TermQuery(new Term("filecontent","some"));
		
		TopDocs topDocs = indexSearcher.search(query, 10);
		
		System.out.println("查询结果的总条数： " + topDocs.totalHits);
		
		for (ScoreDoc scoreDoc: topDocs.scoreDocs){
			
			Document document = indexSearcher.doc(scoreDoc.doc);
			
			System.out.println("名称： " + document.get("filename"));
			//System.out.println("内容： " + document.get("filecontent"));
			System.out.println("路径: " + document.get("filepath"));
			//System.out.println("大小： " + document.get("filesize")); 
		}
		indexReader.close();
	}
}
