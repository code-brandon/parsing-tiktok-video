package com.xiaozheng.tikTokVideo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.xiaozheng.tikTokVideo.dao.VideoDao;
import com.xiaozheng.tikTokVideo.domain.ResolveURL;
import com.xiaozheng.tikTokVideo.domain.SaveVideoVo;
import com.xiaozheng.tikTokVideo.domain.VideoEntity;
import com.xiaozheng.tikTokVideo.utils.MyWebRequest;
import com.xiaozheng.tikTokVideo.utils.ThreadConfig;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName ResolveService
 * @CreateTime 2021/11/25 10:32
 */
public class ResolveService {

    private VideoDao videoDao = new VideoDao();
    private FileOperService fileOperService = new FileOperService();
    /**
     * 解析用户获取喜欢列表
     * @param resolveURL
     * @return
     */
    public String resolve(ResolveURL resolveURL) {
        // System.out.println("resolveURL = " + resolveURL.getCookie());

        // String[] strings = resolveURL.getCookie().split("\r\n");
        // HashMap<String, String> map = new HashMap<>();
        // map.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");
        // map.put("referer", "https://www.douyin.com/user/MS4wLjABAAAA7N-v3emjufRUePS_po1gJaSPxwoEXJ67vCIfUvY3g2o?enter_from=recommend&enter_method=top_bar");
        // map.put("accept", "application/json, text/plain, */*");
        // map.put("cookie", resolveURL.getCookie());
        /*if (Objects.nonNull(strings) && strings.length > 0) {
            for (String string : strings) {
                String[] KV = string.split(": ");
                map.put(KV[0], KV[1]);
            }
        }*/
        // String s = HttpClientUtils.doGet(resolveURL.getUrl(), map);
        final String s = MyWebRequest.webGetRequest(resolveURL.getUrl(), resolveURL.getCookie());
        // System.out.println("-----------------"+s);
        return s;
    }

    /**
     * 保存视频信息
     * @param videoEntity
     * @return
     */
    public boolean saveVideoInfo(VideoEntity videoEntity) {
        return videoDao.saveVideoInfo(videoEntity);
    }

    /**
     * 下载视频
     * @param saveVideoVo
     * @return
     */
    public String downloadVideo(SaveVideoVo saveVideoVo) {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        final String HTTPS = "https:";
        String url = saveVideoVo.getVideoUrl();
        // String url="https://www.douyin.com/video/7027064517230103843?previous_page=others_homepage&modeFrom=userLike&cursor=0&count=10&secUid=MS4wLjABAAAA7N-v3emjufRUePS_po1gJaSPxwoEXJ67vCIfUvY3g2o&enter_method=like";
        WebClient webClient = ThreadConfig.getWebClient();

        HtmlPage page = null;
        try {
            //尝试加载上面图片例子给出的网页
            page = webClient.getPage(url);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            webClient.close();
        }
        //异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束
        webClient.waitForBackgroundJavaScript(300);

        //直接将加载完成的页面转换成xml格式的字符串
        String pageXml = page.asXml();
        Document parse = Jsoup.parse(pageXml);
        final Element render_data = parse.getElementById("RENDER_DATA");

        ResolveService resolveService = new ResolveService();
        VideoEntity videoEntity = new VideoEntity();
        try {
            final String encode = URLDecoder.decode(render_data.html().substring("//<![CDATA[".length(), render_data.html().length() - "//]]>".length()).trim(), "UTF-8");
            JSONObject jsonObject = JSON.parseObject(encode);

            JSONObject c_20 = jsonObject.getJSONObject("C_20");

            JSONObject detail = c_20.getJSONObject("aweme").getJSONObject("detail");
            // 视频ID
            String awemeId = detail.getString("awemeId");
            videoEntity.setAwemeId(awemeId);
            // 用户信息
            JSONObject authorInfo = detail.getJSONObject("authorInfo");
            String nickname = authorInfo.getString("nickname");
            String uid = authorInfo.getString("uid");
            String avatarUri = authorInfo.getString("avatarUri");
            videoEntity.setNickname(nickname);
            videoEntity.setUid(uid);
            videoEntity.setAvatarUri(HTTPS + avatarUri);
            // 描述
            String desc = detail.getString("desc");
            videoEntity.setDesc(desc);
            //标签名称 集合
            List<String> hashtagNames = detail.getJSONArray("textExtra").stream().map(item -> ((JSONObject) item).getString("hashtagName")).collect(Collectors.toList());
            videoEntity.setHashtagNames(String.join(",", hashtagNames));

            JSONObject video = detail.getJSONObject("video");
            // 视频播放Api
            String playApi = video.getString("playApi");
            videoEntity.setPlayApi(HTTPS + playApi);
            // 覆盖视频的图片
            String cover = video.getString("cover");
            videoEntity.setCover(HTTPS + cover);

            videoEntity.setDateAdded(new Date());

            boolean b = resolveService.saveVideoInfo(videoEntity);
            if (b) {
                CompletableFuture<String> infoFuture = CompletableFuture.supplyAsync(() -> {
                    String result = fileOperService.fileUpload(videoEntity.getPlayApi(), videoEntity.getAwemeId(), null);
                    System.out.println("结果："+result);
                    return result;
                },ThreadConfig.threadPoolExecutor());
                return JSON.toJSONString(videoEntity);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
