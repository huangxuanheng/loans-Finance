package com.harry.boostrap.startup.analyze.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.harry.boostrap.startup.analyze.enterprise.ResponseData;
import com.harry.boostrap.startup.analyze.enterprise.liability.AnnualReport;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Harry
 * @date 2021/1/2
 * @des 描述：
 */
public class HttpUtil {

    static CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
    public static <T> T get(String url, TypeReference<T>clazz) throws IOException, URISyntaxException {
        HttpGet get = new HttpGet(url);
//        get.setURI(new URI(url));
//        get.setHeader(new BasicHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"));
        CloseableHttpResponse execute = closeableHttpClient.execute(get);
//        Header[] allHeaders = execute.getAllHeaders();
//        get.setHeaders(allHeaders);
        HttpEntity entity = execute.getEntity();
        String body = EntityUtils.toString(entity);
        if(clazz==null){
            return null;
        }

        ResponseData tResponseData = JSON.parseObject(body, ResponseData.class);
        AnnualReport data = tResponseData.getData();
        String s = JSON.toJSONString(data);
        T t = JSON.parseObject(s, clazz);
        return t;
    }

    public static <T> T getObject(String url,String key, Class<T>clazz) throws IOException {
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse execute = closeableHttpClient.execute(get);
        HttpEntity entity = execute.getEntity();
        String body = EntityUtils.toString(entity);
        if(clazz==null){
            return null;
        }


        JSONObject jsonObject = JSON.parseObject(body);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONObject dataJSONObject = data.getJSONObject(key);
        String s = JSON.toJSONString(dataJSONObject);
        T t = JSON.parseObject(s, clazz);
        return t;
    }

    public static JSONArray getJSONArray(String url,String key) throws IOException {
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse execute = closeableHttpClient.execute(get);
        HttpEntity entity = execute.getEntity();
        String body = EntityUtils.toString(entity);
        JSONObject jsonObject = JSON.parseObject(body);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray dataJSONObject = data.getJSONArray(key);
        return dataJSONObject;
    }

    /**
     * 获取指数PE估值
     * @param url
     * @return
     * @throws IOException
     */
    public static Map<String,Double> getFundPe(String url) throws IOException {
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse execute = closeableHttpClient.execute(get);
        HttpEntity entity = execute.getEntity();
        String body = EntityUtils.toString(entity);
        JSONObject jsonObject = JSON.parseObject(body);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray horizontal_lines = data.getJSONArray("horizontal_lines");
        Map<String,Double>map=new HashMap<>();
        for (int x=0;x<horizontal_lines.size();x++){
            JSONObject linesJSONObject = horizontal_lines.getJSONObject(x);
            if(linesJSONObject.getInteger("line_type")==1){
                map.put("lv",linesJSONObject.getDoubleValue("line_value"));
            }
        }
        //pe 市盈率列表
        JSONArray index_eva_pe_growths = data.getJSONArray("index_eva_pe_growths");
        JSONObject growthsJSONObject = index_eva_pe_growths.getJSONObject(index_eva_pe_growths.size() - 1);

        map.put("pe",growthsJSONObject.getDoubleValue("pe"));

        return map;
    }
}
