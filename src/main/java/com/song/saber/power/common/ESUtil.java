package com.song.power.common;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.indices.CreateIndex;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 001844 on 2018/1/12.
 */
public class ESUtil {
    /*将这些文档写入到ES中，需要搞清楚*/
    private static final JestClient client;

    static {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder("http://198.74.48.82:9200")
                .multiThreaded(true)
                //Per default this implementation will create no more than 2 concurrent connections per given route
                .defaultMaxTotalConnectionPerRoute(2)
                // and no more 20 connections in total
                .maxTotalConnection(20)
                .build());
        client = factory.getObject();
    }

    public static JestResult createIndex(String index) {
        JestResult result = null;
        try {
            result = client.execute(new CreateIndex.Builder(index).build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void test(){
        String settingStr = "\"settings\" : {\n" +
                "        \"number_of_shards\" : 5,\n" +
                "        \"number_of_replicas\" : 1\n" +
                "    }\n";
        Map<String,Object> settings = new HashMap<>();
        settings.put("number_of_shards",5);
        settings.put("number_of_replicas",1);

        try {
            client.execute(new CreateIndex.Builder("articles").settings(settings).build());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
