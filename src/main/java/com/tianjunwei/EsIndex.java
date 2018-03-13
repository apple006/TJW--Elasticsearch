package com.tianjunwei;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class EsIndex {
	
	private final static int port = 9300;
	
	public static void main(String [] args) throws UnknownHostException{
		TransportClient client = client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddresses(new TransportAddress(InetAddress.getByName("localhost"),port));
		Map<String, Object> datas = new HashMap<String,Object>();
		datas.put("key", "values");
		datas.put("key2", "values2");
		client.prepareIndex("index", "type").setSource(datas ).get();
	}

}
