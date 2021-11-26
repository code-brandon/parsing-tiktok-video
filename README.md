# parsing-tiktok-video

#### 介绍

解析抖音视频 返回 解析后的JSON数据 保存视频到minio中

#### 软件架构

```xml
<dependency>
    <groupId>org.jsoup</groupId>
    <artifactId>jsoup</artifactId>
    <version>1.13.1</version>
</dependency>

<dependency>
    <groupId>net.sourceforge.htmlunit</groupId>
    <artifactId>htmlunit</artifactId>
    <version>2.33</version>
</dependency>

<dependency>
    <groupId>cn.wanghaomiao</groupId>
    <artifactId>JsoupXpath</artifactId>
    <version>2.4.0</version>
</dependency>

<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.20</version>
</dependency>

<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpclient</artifactId>
    <version>4.5.13</version>
</dependency>

<dependency>
    <groupId>io.minio</groupId>
    <artifactId>minio</artifactId>
    <version>8.3.3</version>
</dependency>

<!--okhttp依赖-->
<dependency>
    <groupId>com.squareup.okhttp3</groupId>
    <artifactId>okhttp</artifactId>
    <version>4.0.0</version>
</dependency>
<dependency>
    <groupId>com.squareup.okio</groupId>
    <artifactId>okio</artifactId>
    <version>2.10.0</version>
</dependency>

<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.72</version>
</dependency>
```



#### 安装教程

1.  git clone https://gitee.com/zong_zh/parsing-tiktok-video.git
2.  修改`jdbc.properties`文件中的参数
3.  修改`minio.properties`中的参数
4.  启动路径：http://localhost:8080/WebsiteApplet/index.htnl

#### 使用说明

1.  如果只想解析可以去掉下面代码 ：com/xiaozheng/tikTokVideo/service/ResolveService.java:141

    ```java
    if (b) {
        CompletableFuture<String> infoFuture = CompletableFuture.supplyAsync(() -> {
            String result = fileOperService.fileUpload(videoEntity.getPlayApi(), videoEntity.getAwemeId(), null);
            System.out.println("结果："+result);
            return result;
        },ThreadConfig.threadPoolExecutor());
        return JSON.toJSONString(videoEntity);
    }
    ```

    

2.  不太会用异步线程，使用方法有问题，自行修改

3.  `htmlunit` 模拟浏览器 启动慢 ， 请耐心等待解析

4.  修改minio的上传代码 com.xiaozheng.tikTokVideo.service.FileOperService#fileUpload

    4.1 `douyin-video` 是 当前项目的桶名

    4.2 minio开始上传代码

    ```java
    minioClient.putObject(PutObjectArgs.builder().bucket("douyin-video").object(format + "/" + finalFileName).stream(
                            inputStream, -1, 10485760)
                                                  .contentType("video/mp4")
                                                  .build());
    // 上传完毕拼接minio请求地址
    result = "https://data.smallblog.cn" + "/douyin-video/" + format + "/" + finalFileName;
    ```

![](https://data.smallblog.cn/blog-images/202111/ba3df8af4df711ec957e005056c00008.png)

来一个好看的猫：https://www.douyin.com/video/7013941643233529121

<video height="650px" src = "https://data.smallblog.cn/douyin-video/2021/11/7013941643233529121.mp4">
