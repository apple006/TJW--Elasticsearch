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
 
        // 将采集到的数据封装到Document对象中
        List<Document> docList = new ArrayList<Document>();
        List<Book> list = new ArrayList<Book>();
        Book book1 = new Book();
        book1.setDescription("java lucene");
        book1.setId(1);
        book1.setName("java编程");
        book1.setPrice(100.0f);
        Book book2 = new Book();
        book2.setDescription("C");
        book2.setId(2);
        book2.setName("c编程");
        book2.setPrice(100.0f);
        list.add(book1);
        list.add(book2);
        Document document;
        for (Book book : list) {
            document = new Document();
            // store:如果是yes，则说明存储到文档域中
            // 图书ID
            // Field id = new TextField("id", book.getId().toString(), Store.YES);
 
            Field id = new TextField("id", Integer.toString(book.getId()), Field.Store.YES);
            // 图书名称
            Field name = new TextField("name", book.getName(), Field.Store.YES);
            // 图书价格
            Field price = new TextField("price", Float.toString(book.getPrice()), Field.Store.YES);
            // 图书图片地址
            // 图书描述
            Field description = new TextField("description", book.getDescription(), Field.Store.YES);
 
            // 将field域设置到Document对象中
            document.add(id);
            document.add(name);
            document.add(price);
            document.add(description);
 
            docList.add(document);
        }
        //JDK 1.7以后 open只能接收Path/////////////////////////////////////////////////////
 
        // 创建分词器，标准分词器
        Analyzer analyzer = new StandardAnalyzer();
 
        // 创建IndexWriter
        // IndexWriterConfig cfg = new IndexWriterConfig(Version.LUCENE_6_5_0,analyzer);
        IndexWriterConfig cfg = new IndexWriterConfig(analyzer);
 
        // 指定索引库的地址
         File indexFile = new File("lucene_index");
         if(!indexFile.exists()){
        	 indexFile.mkdirs();
         }
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(indexFile.getAbsolutePath()));
 
        IndexWriter writer = new IndexWriter(directory, cfg);
        
        writer.deleteAll(); //清除以前的index
        // 通过IndexWriter对象将Document写入到索引库中
        for (Document doc : docList) {
            writer.addDocument(doc);
        }
 
        // 关闭writer
        writer.close();
    }

}
