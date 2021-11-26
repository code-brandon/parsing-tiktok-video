package com.xiaozheng.tikTokVideo.service;

import com.xiaozheng.tikTokVideo.dao.VideoDao;
import com.xiaozheng.tikTokVideo.utils.MinioUtils;
import com.xiaozheng.tikTokVideo.utils.ThreadConfig;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName FileOperService
 * @CreateTime 2021/11/25 19:50
 */
public class FileOperService {
    VideoDao videoDao = new VideoDao();
    public String fileUpload(String url,String awemeId, String fileName) {
        CompletableFuture<String> infoFuture = CompletableFuture.supplyAsync(() -> {
            String result = "";
            try {
                // 参数为：图床，账号，密码
                MinioClient minioClient =
                        MinioClient.builder()
                                .endpoint(MinioUtils.getEndpoint())
                                .credentials(MinioUtils.getAccesskey(), MinioUtils.getSecretKey())
                                .build();

                // 检查文件夹是否已经存在
                boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket("douyin-video").build());
                if (found) {
                    System.out.println("我的桶名存在");
                } else {
                    System.out.println("我的桶名不存在");
                    // 创建桶
                    minioClient.makeBucket(MakeBucketArgs.builder().bucket("douyin-video").build());
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM");
                String format = simpleDateFormat.format(new Date());

                String finalFileName = (!"".equals(fileName) ? awemeId : fileName) + ".mp4";
                URLConnection conn = new URL(url).openConnection();
                InputStream inputStream = conn.getInputStream();

                // 使用putObject上传一个文件到文件夹中。
                //参数为：文件夹，要存成的名字，要存的文件
                minioClient.putObject(PutObjectArgs.builder().bucket("douyin-video").object(format + "/" + finalFileName).stream(
                        inputStream, -1, 10485760)
                                              .contentType("video/mp4")
                                              .build());
                result = "https://data.smallblog.cn" + "/douyin-video/" + format + "/" + finalFileName;
                videoDao.updateMinioUriByAwemeId(awemeId, result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }, ThreadConfig.threadPoolExecutor());
        try {
            return infoFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "";
    }
}
