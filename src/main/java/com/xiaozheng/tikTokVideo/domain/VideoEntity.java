package com.xiaozheng.tikTokVideo.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName VideoEntity
 * @CreateTime 2021/11/25 16:53
 */
@Data
public class VideoEntity implements Serializable {
    /**
     * 视频ID
     */
    private String awemeId;

    /**
     * UP昵称
     */
    private String nickname;

    /**
     * UP ID
     */
    private String uid;

    /**
     * UP头像
     */
    private String avatarUri;

    /**
     * 视频描述
     */
    private String desc;

    /**
     * 视频标签集合
     */
    private String hashtagNames;

    /**
     * 视频播放Api 视频
     */
    private String playApi;

    /**
     * 视频图片
     */
    private String cover;

    /**
     * 添加日期
     */
    private Date dateAdded;

    /**
     * 浏览次数
     */
    private Long views;

}
