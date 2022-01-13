package com.xiaozheng.tikTokVideo.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * What -- 解析URL
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName ResolveURL
 * @CreateTime 2021/11/25 10:24
 */
@Data
public class ResolveURL implements Serializable {

    private String url;

    private String cookie;
}
