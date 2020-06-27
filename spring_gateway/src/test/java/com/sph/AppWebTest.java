package com.sph;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
public class AppWebTest {

    private String token;

    /**
     * 登陆
     * @throws Exception
     */
    @Test
    public void loginTest() throws Exception {
        String url = "http://127.0.0.1:8090/APP-WEB/auth?userName=admin&password=admin";
        Map<String, String> params = new HashMap<>();
        String str = getResult(url,params,null);
        JSONObject object = (JSONObject) JSONObject.parse(str);
        token = object.getJSONObject("data").getString("token");
        System.out.println(str);
    }

    /**
     * 注册
     * @throws Exception
     */
    @Test
    public void registerTest() throws Exception {
        String url = "http://127.0.0.1:8090/APP-WEB/register";
        Map<String, String> params = new HashMap<>();
        params.put("username","usertest");
        params.put("password","123456");
        String str = getResult(url,params,null);
        System.out.println(str);
    }

    /**
     * app-web搜索新闻列表
     * @throws Exception
     */
    @Test
    public void searchNewsTest() throws Exception {
        loginTest();
        String url = "http://127.0.0.1:8090/APP-WEB/searchNews";
        Map<String, String> params = new HashMap<>();
        String str = getResult(url,params,token);
        System.out.println(str);
    }

    /**
     * app-web查看单条内容
     * @throws Exception
     */
    @Test
    public void readNewsTest() throws Exception {
        loginTest();
        String url = "http://127.0.0.1:8090/APP-WEB/readNews?id=1";
        Map<String, String> params = new HashMap<>();
        String str = getResult(url,params,token);
        System.out.println(str);
    }

    /**
     * admin-web搜索新闻列表
     * @throws Exception
     */
    @Test
    public void searchNewsByAdminTest() throws Exception {
        loginTest();
        String url = "http://127.0.0.1:8090/ADMIN-WEB/searchNews";
        Map<String, String> params = new HashMap<>();
        String str = getResult(url,params,token);
        System.out.println(str);
    }

    /**
     * admin-web查看单条内容
     * @throws Exception
     */
    @Test
    public void readNewsByAdminTest() throws Exception {
        loginTest();
        String url = "http://127.0.0.1:8090/ADMIN-WEB/readNews?id=1";
        Map<String, String> params = new HashMap<>();
        String str = getResult(url,params,token);
        System.out.println(str);
    }

    /**
     * admin-web新增新闻
     * @throws Exception
     */
    @Test
    public void addNewsTest() throws Exception {
        loginTest();
        String url = "http://127.0.0.1:8090/ADMIN-WEB/addNews";
        Map<String, String> params = new HashMap<>();
        params.put("newsTitle","新闻标题");
        params.put("newsContent","新闻内容");
        String str = getResult(url,params,token);
        System.out.println(str);
    }

    /**
     * admin-web编辑新闻
     * @throws Exception
     */
    @Test
    public void updateNewsTest() throws Exception {
        loginTest();
        String url = "http://127.0.0.1:8090/ADMIN-WEB/updateNews";
        Map<String, String> params = new HashMap<>();
        params.put("id","2");
        params.put("newsTitle","修改新闻标题");
        params.put("newsContent","修改新闻内容");
        String str = getResult(url,params,token);
        System.out.println(str);
    }


    public static String getResult(String url,Map<String, String> params,String authorization){
        String returnValue = null;
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);
        try {
            HttpPost httppost = new HttpPost(url);
            httppost.addHeader("Content-type","application/json; charset=utf-8");
            httppost.setHeader("Accept", "application/json");
            if(StringUtils.isNotBlank(authorization)){
                httppost.setHeader("Authorization", authorization);
            }

            StringEntity entity = new StringEntity(JSONObject.toJSONString(params), Charset.forName("UTF-8"));
            httppost.setEntity(entity);

            HttpResponse resp = httpclient.execute(httppost);
            if(resp.getStatusLine().getStatusCode() == 200) {
                HttpEntity he = resp.getEntity();
                String   respContent = EntityUtils.toString(he,"UTF-8");
                returnValue =  respContent;
            }
        }
        catch (SocketTimeoutException e) {
            e.printStackTrace();
            returnValue = "timeout";
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 关闭连接,释放资源
            httpclient.getConnectionManager().shutdown();
        }
        return  returnValue;
    }

}
