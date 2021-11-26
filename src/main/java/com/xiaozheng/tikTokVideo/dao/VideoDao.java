package com.xiaozheng.tikTokVideo.dao;

import com.xiaozheng.tikTokVideo.domain.VideoEntity;
import com.xiaozheng.tikTokVideo.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * What --
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName VideoDao
 * @CreateTime 2021/11/25 17:23
 */
public class VideoDao {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    public boolean saveVideoInfo(VideoEntity videoEntity) {
        try {
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement("insert INTO douyin_video (awemeId,nickname,uid,avatar_uri,`desc`,hashtag_names,play_api,cover,date_added,views) VALUES (?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, videoEntity.getAwemeId());
            preparedStatement.setString(2, videoEntity.getNickname());
            preparedStatement.setString(3, videoEntity.getUid());
            preparedStatement.setString(4, videoEntity.getAvatarUri());
            preparedStatement.setString(5, videoEntity.getDesc());
            preparedStatement.setString(6, videoEntity.getHashtagNames());
            preparedStatement.setString(7, videoEntity.getPlayApi());
            preparedStatement.setString(8, videoEntity.getCover());
            preparedStatement.setTimestamp(9, new Timestamp(videoEntity.getDateAdded().getTime()));
            preparedStatement.setLong(10, 0);
            int update = preparedStatement.executeUpdate();
            if(update > 0) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtil.close(null,preparedStatement,connection);
        }
        return false;
    }

    public boolean updateMinioUriByAwemeId(String awemeId, String result) {
        try {
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `bugfood`.`douyin_video` SET `minio_uri` = ? WHERE `awemeId` = ?");
            preparedStatement.setString(1, result);
            preparedStatement.setString(2, awemeId);
            int update = preparedStatement.executeUpdate();
            if(update > 0) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtil.close(null,preparedStatement,connection);
        }
        return false;
    }
}
