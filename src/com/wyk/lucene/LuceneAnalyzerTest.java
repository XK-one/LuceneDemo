package com.wyk.lucene;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.junit.Test;
/**
 * @author Administrator
 * 作用： 分词器的分词效果
 *
 */
public class LuceneAnalyzerTest {
	
	//分词器的分词效果
	@Test
	public void testTokenStream() throws IOException{ 
		
		Analyzer analyzer = new StandardAnalyzer(); 	//单字分词器
		
		TokenStream tokenStream = analyzer.tokenStream("test"
				,"The Spring Framework provides a comprehensive programming and configuration model.");
		//添加一个引用，可以获得每一个关键词
		CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
		//添加一个偏移量的引用，记录关键词的开始位置及结束位置
		OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
		//将指针调整到列表的头部
		tokenStream.reset();
		
		while (tokenStream.incrementToken()){
			//关键词的起始位置
			System.out.println("开始 位置 ： " + offsetAttribute.startOffset());
			//取关键词
			System.out.println("关键词： " + charTermAttribute);
			//关键词的结束位置
			System.out.println("结束 位置： " + offsetAttribute.endOffset() );
			
		}
		tokenStream.close();
	}

}
