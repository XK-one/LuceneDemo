package com.wyk.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
/**
 * @author Administrator
 * 作用: 创建索引
 *
 */
public class LuceneIndexWriter {
	
	//创建索引
	@Test
	public void createIndex() throws IOException {
		
		FSDirectory directory = FSDirectory.open(new File("D:\\project\\lucene_index"));
		//分词器
		StandardAnalyzer analyzer = new StandardAnalyzer(); 
		
		//索引IndexWriter的配置
		IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
		//indexWriter索引类
		IndexWriter indexWriter = new IndexWriter(directory,config); 
		
		File dir = new File("d:\\project\\lucene_document");
		
		for (File file:dir.listFiles()){
			//获取文件中的信息
			String fileName = file.getName();
			String fileContent = FileUtils.readFileToString(file); 
			String filePath = file.getPath();
			long fileSize = FileUtils.sizeOf(file);
			//创建域
			Field nameField = new TextField("filename",fileName,Store.YES);
			Field contentField = new TextField("filecontent",fileContent,Store.YES);
			Field pathField = new StoredField("filepath", filePath);
			Field sizeField = new LongField("filesize", fileSize, Store.YES);
			
			Document document = new Document();
			document.add(nameField);
			document.add(contentField);
			document.add(pathField);
			document.add(sizeField);
			
			//创建索引，并写入索引库
			indexWriter.addDocument(document);
		}
		//关闭IndexWriter
		indexWriter.close();
		
	}
}
