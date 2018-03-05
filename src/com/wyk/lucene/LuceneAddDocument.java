package com.wyk.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * @author Administrator
 * 作用: 索引库的维护
 */
public class LuceneAddDocument {
	
	/**向索引库添加document对象*/
	@Test
	public void addDocument() throws IOException {
		
		IndexWriter indexWriter = getIndexWriter();
		Document document = new Document();
		
		document.add(new TextField("filename","新添加的文档名称",Store.YES));
		document.add(new TextField("filecontent","新添加的文档内容01",Store.NO)); 
		document.add(new TextField("filecontent", "新添加的文档内容02",Store.YES));
		document.add(new TextField("filecontent1", "新添加的文档内容03",Store.YES));
		indexWriter.addDocument(document);
		
		indexWriter.close();
		
	}
	
	@Test
	/**删除全部索引*/
	public void deleteAllIndex() throws IOException{
		
		IndexWriter indexWriter = getIndexWriter();
		indexWriter.deleteAll();
		indexWriter.close();
	}
	
	/**根据条件删除索引*/
	@Test
	public void deleteIndexByQuery() throws IOException {
		IndexWriter indexWriter = getIndexWriter();
		Query query = new TermQuery(new Term("filecontent","http"));
		indexWriter.deleteDocuments(query);
		indexWriter.close();
	}
	
	@Test
	/**索引库的修改*/
	public void updateIndex() throws IOException{
		
		IndexWriter indexWriter = getIndexWriter();
		
		Document document = new Document();
		document.add(new TextField("filename", "今天 元宵节", Store.YES));
		document.add(new TextField("filecontent", "今天正月 15 元宵节",Store.YES)); 
		
		indexWriter.updateDocument(new Term("filecontent","one"), document);
		indexWriter.close();
	}
	
	/**专门提供indexWriter对象*/
	public IndexWriter getIndexWriter() {
		IndexWriter indexWriter = null;
		try {
			//索引存放的位置
			FSDirectory directory = FSDirectory.open(new File("D:\\project\\lucene_index"));
			//生成索引的配置
			IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, new IKAnalyzer());
					
			indexWriter = new IndexWriter(directory, config);
		} catch (IOException e) { 
			//e.printStackTrace();
		}
		return indexWriter;
	}

}
