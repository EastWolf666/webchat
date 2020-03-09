package com.amayadream.webchat.websocket;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpRobot {
    public static String postJson(String url, String jsonString) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            post.setEntity(new ByteArrayEntity(jsonString.getBytes(StandardCharsets.UTF_8)));
            response = httpClient.execute(post);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                result = entityToString(entity);
                JSONObject jsonObject = JSONObject.parseObject(result); // 将字符串转化为JSONObject类型
                /*
                 * 分析 JSON的中的数据的格式，提取出来 System.out.println("jsonObject --->" + jsonObject);
                 * jsonObject ---> {"intent":{"actionName":"","code":7110,"intentName":""},
                 * "results":[{"groupType":0,"resultType":"text","values":{"text":"你的小可爱做错了什么吗？"
                 * }}]}
                 *
                 */
                JSONArray ja = jsonObject.getJSONArray("results");
                //System.out.println("jsonArray ---> " + ja);
                JSONObject jo = ja.getJSONObject(ja.size() - 1); // 提取出最后一个 text类型的数据-
                jsonObject = jo.getJSONObject("values");
                result = jsonObject.getString("text"); // 取出 value 中的 text数据
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String entityToString(HttpEntity entity) throws IOException {
        String result = null;
        if (entity != null) {
            long lenth = entity.getContentLength();
            if (lenth != -1 && lenth < 2048) {
                result = EntityUtils.toString(entity, "UTF-8");
            } else {
                InputStreamReader reader1 = new InputStreamReader(entity.getContent(), StandardCharsets.UTF_8);
                CharArrayBuffer buffer = new CharArrayBuffer(2048);
                char[] tmp = new char[1024];
                int l;
                while ((l = reader1.read(tmp)) != -1) {
                    buffer.append(tmp, 0, l);
                }
                result = buffer.toString();
            }
        }
        return result;
    }

    public static JSONObject transJosn(String text) { // 构造JSON请求参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("reqType", "0");

        JSONObject perception = new JSONObject();
        JSONObject inputText = new JSONObject();
        inputText.put("text", text);
        perception.put("inputText", inputText);
        jsonObject.put("perception", perception);

        JSONObject userInfo = new JSONObject();
        userInfo.put("apiKey", "456d4e3e6ebf4d3dadefbcdcdfa604a1");
        //userInfo.put("apiKey", "cd0e7517b3d9454db500004d0068f5ff");
        userInfo.put("userId", "951c61404fdecd3");
        //userInfo.put("userId", "b1c8e38a49eaf13");
        jsonObject.put("userInfo", userInfo);

        return jsonObject;
    }
}
