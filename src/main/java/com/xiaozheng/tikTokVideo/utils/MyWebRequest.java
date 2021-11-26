package com.xiaozheng.tikTokVideo.utils;

import okhttp3.*;

import java.util.List;
import java.util.Objects;

/**
 * What -- 封装的请求
 * <br>
 * Describe --
 * <br>
 *
 * @Package: com.xiaozheng.wallpaper_api.utils
 * @ClassName: MyWebRequest
 * @Author: 小政同学    QQ:xiaozheng666888@qq.com
 * @CreateTime: 2021/2/7 18:01
 */
public class MyWebRequest {
    /**
     * GET请求
     * @param url
     * @return
     */
    public static String webGetRequest(String url,String cookie) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                                      .url(url)
                                      .method("GET", null)
                                      .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36")
                                      .addHeader("referer", "https://www.douyin.com/user/MS4wLjABAAAA7N-v3emjufRUePS_po1gJaSPxwoEXJ67vCIfUvY3g2o?enter_from=recommend&enter_method=top_bar")
                                      .addHeader("accept", "application/json, text/plain, */*")
                                      .addHeader("cookie",cookie )
                                      .build();
            Response response = client.newCall(request).execute();
            return Objects.requireNonNull(response.body()).string();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * POST请求
     * @param url
     * @return
     */
    public static String webPostRequest(String url) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .method("POST", null)
                    .addHeader("User-Agent", randomUserAgent())
                    .build();
            Response response = client.newCall(request).execute();
            return Objects.requireNonNull(response.body()).string();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * GET请求 返回 Response
     * @param url
     * @return Response
     */
    public static Response webGetRequestMap(String url, RequestBody body) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                                          .build();
            Request request = new Request.Builder()
                                      .url(url)
                                      .method("GET", body)
                                      .addHeader("User-Agent", randomUserAgent())
                                      .build();
            Response response = client.newCall(request).execute();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * POST请求 返回 Response
     * @param url
     * @return Response
     */
    public static Response webPostRequestMap(String url,RequestBody body) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                                          .build();
            Request request = new Request.Builder()
                                      .url(url)
                                      .method("POST", body)
                                      .addHeader("User-Agent", randomUserAgent())
                                      .build();
            Response response = client.newCall(request).execute();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取GET方法的Cookie
     * @param url
     * @return
     */
    public static String webGetRequestCookie(String url) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                                          .build();
            Request request = new Request.Builder()
                                      .url(url)
                                      .method("GET", null)
                                      .addHeader("User-Agent", randomUserAgent())
                                      .build();
            Response response = client.newCall(request).execute();
            //获取头信息
            Headers  headers=response.headers();
            //获取cookie
            List<String> cookies = headers.values("Set-Cookie");
            return Objects.requireNonNull(String.join(";", cookies));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取POST方法的Cookie
     * @param url
     * @return
     */
    public static String webPostRequestCookie(String url) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                                          .build();
            Request request = new Request.Builder()
                                      .url(url)
                                      .method("POST", null)
                                      .addHeader("User-Agent", randomUserAgent())
                                      .build();
            Response response = client.newCall(request).execute();
            //获取头信息
            Headers  headers=response.headers();
            //获取cookie
            List<String> cookies = headers.values("Set-Cookie");
            return Objects.requireNonNull(String.join(";", cookies));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回随机的UserAgent
     * @return
     */
    public static String randomUserAgent() {
        String[] userAgents = {
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36",
                "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1",
                "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1",
                "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Mobile Safari/537.36",
                "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Mobile Safari/537.36",
                "Mozilla/5.0 (Linux; Android 5.1.1; Nexus 6 Build/LYZ28E) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Mobile Safari/537.36",
                "Mozilla/5.0 (iPhone; CPU iPhone OS 10_3_2 like Mac OS X) AppleWebKit/603.2.4 (KHTML, like Gecko) Mobile/14F89;GameHelper",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/603.2.4 (KHTML, like Gecko) Version/10.1.1 Safari/603.2.4",
                "Mozilla/5.0 (iPhone; CPU iPhone OS 10_0 like Mac OS X) AppleWebKit/602.1.38 (KHTML, like Gecko) Version/10.0 Mobile/14A300 Safari/602.1",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:46.0) Gecko/20100101 Firefox/46.0",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:46.0) Gecko/20100101 Firefox/46.0",
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)",
                "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)",
                "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)",
                "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Win64; x64; Trident/6.0)",
                "Mozilla/5.0 (Windows NT 6.3; Win64, x64; Trident/7.0; rv:11.0) like Gecko",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/13.10586",
                "Mozilla/5.0 (iPad; CPU OS 10_0 like Mac OS X) AppleWebKit/602.1.38 (KHTML, like Gecko) Version/10.0 Mobile/14A300 Safari/602.1"
        };
        int num = (int) Math.floor(Math.random() * userAgents.length);
        return userAgents[num];
    }

}