package com.xiaozheng.tikTokVideo.utils;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName MinioUtils
 * @CreateTime 2021/11/25 20:58
 */
@Data
public class MinioUtils implements Serializable {
    private static String endpoint;
    private static String accesskey;
    private static String secretKey;

    public static String getEndpoint() {
        return endpoint;
    }

    public static void setEndpoint(String endpoint) {
        MinioUtils.endpoint = endpoint;
    }

    public static String getAccesskey() {
        return accesskey;
    }

    public static void setAccesskey(String accesskey) {
        MinioUtils.accesskey = accesskey;
    }

    public static String getSecretKey() {
        return secretKey;
    }

    public static void setSecretKey(String secretKey) {
        MinioUtils.secretKey = secretKey;
    }

    static {
        // 使用类加载器 获取类目录下的文件 并返回该文件的流
        InputStream resource = MinioUtils.class.getClassLoader().getResourceAsStream("com/xiaozheng/tikTokVideo/minio.properties");
        // 创建 Properties对象 该对象用于获取 .properties文件    中的值
        Properties properties = new Properties();
        try {
            // 加载.properties文件的流
            properties.load(resource);

            // 获取.properties文件中的数据
            endpoint = properties.getProperty("minio.endpoint");
            accesskey = properties.getProperty("minio.accesskey");
            secretKey = properties.getProperty("minio.secretKey");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
