package com.app.util;


import com.alibaba.fastjson.JSONObject;
import com.app.input.LogMessage;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.ArrayList;
import java.util.List;

public class ESUtil {

    private static String username = "default";
    private static String password = "default";
    private static String hosts = "default";
    private static RestHighLevelClient client = null;

    static {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username, password));
        try {
            List<HttpHost> httpHosts = getEsAddresses(hosts);
            HttpHost[] hList = httpHosts.toArray(new HttpHost[httpHosts.size()]);
            client = new RestHighLevelClient(RestClient.builder(
                    hList)
                    .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {

                        @Override
                        public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                            // TODO Auto-generated method stub
                            return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                        }
                    })
            );
        } catch (Exception e) {
            System.out.println("es connect failed!" + e);
        }
    }


    public static void index(String index, String type, String data) throws Exception {
        IndexRequest request = new IndexRequest(
                index,
                type);

        request.source(data, XContentType.JSON);

        client.index(request);

    }

    public static SearchResponse search(String index, String type) throws Exception {
        SearchRequest searchRequest = new SearchRequest().indices(index).types(type);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        SearchResponse sResponse = client.search(searchRequest);
        return sResponse;
    }


    public static List<HttpHost> getEsAddresses(String hosts) throws Exception {
        String[] hostList = hosts.split(",");
        List<HttpHost> addresses = new ArrayList<>();
        for (String host : hostList) {

            String[] parts = host.split(":", 2);
            if (parts.length > 1) {
                addresses.add(new HttpHost(parts[0], Integer.parseInt(parts[1])));
            } else {
                throw new Exception("invalid elasticsearch hosts format");
            }

        }
        return addresses;
    }

    public static void main(String[] args) throws Exception {
        String jsonString = "log";
        LogMessage<JSONObject> value = JSONObject.parseObject(jsonString, LogMessage.class);
        JSONObject data = value.getData();

        JSONObject es = new JSONObject();
        es.put("group", value.getGroup());
        es.put("user", user);
        es.put("errmsg", data.getString("errmsg"));
        es.put("errcode", data.getInteger("errcode"));

        SearchResponse sResponse=search("log","error");

        SearchHits hits = sResponse.getHits();
        int totalShards = sResponse.getTotalShards();

        System.out.println(totalShards);
        client.close();

    }

}



