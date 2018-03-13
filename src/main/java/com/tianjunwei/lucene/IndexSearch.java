package com.tianjunwei.lucene;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexSearch {
	
	public static void main(String [] args) throws ParseException{
		 Analyzer analyzer = new StandardAnalyzer();
	        // ʹ��QueryParser����ʱ����Ҫָ���ִ���������ʱ�ķִ���Ҫ������ʱ�ķִ���һ��
	        // ��һ��������Ĭ���������������
	        QueryParser parser = new QueryParser("description", analyzer);
	 
	        // ͨ��queryparser������query����
	        // �����������lucene�Ĳ�ѯ���(�ؼ���һ��Ҫ��д)
	        Query query = parser.parse("description:java OR lucene");
	 
	        doSearch(query);
	}
	
	 public static void doSearch(Query query) {
	        // ����IndexSearcher
	        // ָ��������ĵ�ַ
	        try {
//	          File indexFile = new File("D:\\Lpj\\Eclipse\\lecencedemo\\");
//	          Directory directory = FSDirectory.open(indexFile);
	            // 1������Directory
	            //JDK 1.7�Ժ� openֻ�ܽ���Path
	        	 File indexFile = new File("lucene_index");
	             if(!indexFile.exists()){
	            	 indexFile.mkdirs();
	             }
	            Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(indexFile.getAbsolutePath()));
	            IndexReader reader = DirectoryReader.open(directory);
	            IndexSearcher searcher = new IndexSearcher(reader);
	            // ͨ��searcher������������
	            // �ڶ���������ָ����Ҫ��ʾ�Ķ�����¼��N��
	            TopDocs topDocs = searcher.search(query, 10);
	 
	            // ���ݲ�ѯ����ƥ����ļ�¼����
	            int count = (int) topDocs.totalHits;
	            System.out.println("ƥ����ļ�¼����:" + count);
	            // ���ݲ�ѯ����ƥ����ļ�¼
	            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
	 
	            for (ScoreDoc scoreDoc : scoreDocs) {
	                // ��ȡ�ĵ���ID
	                int docId = scoreDoc.doc;
	 
	                // ͨ��ID��ȡ�ĵ�
	                Document doc = searcher.doc(docId);
	                System.out.println("��ƷID��" + doc.get("id"));
	                System.out.println("��Ʒ���ƣ�" + doc.get("name"));
	                System.out.println("��Ʒ�۸�" + doc.get("price"));
	                System.out.println("==========================");
	                // System.out.println("��Ʒ������" + doc.get("description"));
	            }
	            // �ر���Դ
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }

}
