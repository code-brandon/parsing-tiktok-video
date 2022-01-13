package com.xiaozheng.tikTokVideo.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * What -- 保存视频实体类
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName SaveVideoVo
 * @CreateTime 2021/11/25 10:25
 */
@Data
public class SaveVideoVo implements Serializable {
    private String videoUrl;
    private String fileName;
}
