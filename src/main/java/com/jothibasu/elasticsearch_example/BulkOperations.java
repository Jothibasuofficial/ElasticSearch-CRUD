package com.jothibasu.elasticsearch_example;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

public class BulkOperations {

	private static final String HOST = "localhost";
	private static final int PORT_ONE = 9200;
	private static final int PORT_TWO = 9201;
	private static final String SCHEME = "http";

	private static final String INDEX = "persondata";
	private static final String TYPE = "person";

	public static void main(String[] args) throws IOException {

		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost(HOST, PORT_ONE, SCHEME), new HttpHost(HOST, PORT_TWO, SCHEME)));

		/*BulkRequest request = new BulkRequest();
		request.add(new IndexRequest(INDEX, TYPE).id("2").source(XContentType.JSON, "field", "foo2"));
		request.add(new IndexRequest(INDEX, TYPE).id("3").source(XContentType.JSON, "field", "foo3"));

		BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);*/
		
		MultiGetRequest getRequest = new MultiGetRequest();
		getRequest.add(new MultiGetRequest.Item(INDEX,TYPE, "1"));
		
		MultiGetResponse getResponse = client.mget(getRequest, RequestOptions.DEFAULT);
		
		MultiGetItemResponse firstItem = getResponse.getResponses()[0];
		
		GetResponse firstGet = firstItem.getResponse();
		
		if (firstGet.isExists()) {
		           
		    Map<String, Object> sourceAsMap = firstGet.getSourceAsMap(); 
		    
		    
		    
		    for (Map.Entry item : sourceAsMap.entrySet()) {
		          System.out.println("Key: "+item.getKey() + " & Value: " + item.getValue());
		        }
		    
		    //System.out.println("Bulk getRequest " + sourceAsMap);
		             
		}
		
		
		client.close();
	}

}
