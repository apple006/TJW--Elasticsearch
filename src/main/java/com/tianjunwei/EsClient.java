package com.tianjunwei;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class EsClient {

	private final static int port = 9300;
	
   public static void main(String [] args) throws IOException{
	   TransportClient client = client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddresses(new TransportAddress(InetAddress.getByName("localhost"),port));
	   IndexResponse response = client.prepareIndex("msg", "tweet", "1").setSource(XContentFactory.jsonBuilder()
               .startObject().field("userName", "张三")
               .field("sendDate", new Date())
               .field("msg", "msg")
               .endObject()).get();
	   IndexResponse response2 = client.prepareIndex("msg", "tweet", "2").setSource(XContentFactory.jsonBuilder()
               .startObject().field("userName", "张三")
               .field("sendDate", new Date())
               .field("msg", "msg")
               .endObject()).get();
	 /*  String jsonStr = "{\"name\":\"tom zhang\",\"age\":19}";
	   IndexResponse responseJson = client.prepareIndex("msg", "tweet", "1").setSource(jsonStr).get();*/
	   GetResponse getResponse = client.prepareGet("msg", "tweet", "1").get();
	   System.err.println(getResponse.getSourceAsString());
   }

}