package com.tianjunwei.lucene;

import java.io.File;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.tianjunwei.lucene.bean.Book;

public class IndexCreate {
	
	public static void main(String [] args){
		try {
			createIndex();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void createIndex() throws Exception {
 
        // ���ɼ��������ݷ�װ��Document������
        List<Document> docList = new ArrayList<Document>();
        List<Book> list = new ArrayList<Book>();
        Book book1 = new Book();
        book1.setDescription("java lucene");
        book1.setId(1);
        book1.setName("java���");
        book1.setPrice(100.0f);
        Book book2 = new Book();
        book2.setDescription("C");
        book2.setId(2);
        book2.setName("c���");
        book2.setPrice(100.0f);
        list.add(book1);
        list.add(book2);
        Document document;
        for (Book book : list) {
            document = new Document();
            // store:�����yes����˵���洢���ĵ�����
            // ͼ��ID
            // Field id = new TextField("id", book.getId().toString(), Store.YES);
 
            Field id = new TextField("id", Integer.toString(book.getId()), Field.Store.YES);
            // ͼ������
            Field name = new TextField("name", book.getName(), Field.Store.YES);
            // ͼ��۸�
            Field price = new TextField("price", Float.toString(book.getPrice()), Field.Store.YES);
            // ͼ��ͼƬ��ַ
            // ͼ������
            Field description = new TextField("description", book.getDescription(), Field.Store.YES);
 
            // ��field�����õ�Document������
            document.add(id);
            document.add(name);
            document.add(price);
            document.add(description);
 
            docList.add(document);
        }
        //JDK 1.7�Ժ� openֻ�ܽ���Path/////////////////////////////////////////////////////
 
        // �����ִ�������׼�ִ���
        Analyzer analyzer = new StandardAnalyzer();
 
        // ����IndexWriter
        // IndexWriterConfig cfg = new IndexWriterConfig(Version.LUCENE_6_5_0,analyzer);
        IndexWriterConfig cfg = new IndexWriterConfig(analyzer);
 
        // ָ��������ĵ�ַ
         File indexFile = new File("lucene_index");
         if(!indexFile.exists()){
        	 indexFile.mkdirs();
         }
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(indexFile.getAbsolutePath()));
 
        IndexWriter writer = new IndexWriter(directory, cfg);
        
        writer.deleteAll(); //�����ǰ��index
        // ͨ��IndexWriter����Documentд�뵽��������
        for (Document doc : docList) {
            writer.addDocument(doc);
        }
 
        // �ر�writer
        writer.close();
    }

}
