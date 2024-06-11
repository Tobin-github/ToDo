package top.tobin.component.service;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * media组件接口定义
 */
public interface IMediaComp {
    /**
     * 返回组件名称，作为组件唯一标识
     */
    String getMediaCompName();

    /**
     * 获取当前播放信息，用于初始化播放信息
     */
    MediaInfo getMediaInfo();

    /**
     * 跳转到音源播放页，点击时可以跳转到对应的组件页面
     */
    int jumpToMedia();

    /**
     * 播放
     */
    int play();

    /**
     * 暂停
     */

    int pause();

    /**
     * 是否在播放
     */
    @Deprecated
    default boolean isPlay() {
        return false;
    }

    ;

    /**
     * 是否收藏
     */
    @Deprecated
    default boolean isFavorite() {
        return false;
    }

    /**
     * 下一首
     */
    int next();

    /**
     * 添加收藏
     */
    int addFavorite();

    /**
     * 删除收藏
     */
    int removeFavorite();

    @StringDef({MediaType.MEDIA_TYPE_ONLINE, MediaType.MEDIA_TYPE_RADIO,
            MediaType.MEDIA_TYPE_BT, MediaType.MEDIA_TYPE_USB})
    @Retention(RetentionPolicy.SOURCE)
    @interface MediaType {
        String MEDIA_TYPE_ONLINE = "media_type_online";
        String MEDIA_TYPE_RADIO = "media_type_radio";
        String MEDIA_TYPE_BT = "media_type_bt";
        String MEDIA_TYPE_USB = "media_type_usb";
    }

    public static class MediaInfo {
        //必须设置，此消息属于哪个组件
        public String media_comp;
        //歌手
        public String single;
        //歌名
        public String songName;
        //封面
        public String coverUrl;
        //是否支持收藏
        public boolean isSupportFavorite;
        //是否收藏
        public boolean isFavorite;
        //初始化播放状态
        public boolean isPlay;
        //是否可点击收藏
        public boolean likeEnable;


        @Override
        public String toString() {
            return "MediaInfo{" +
                    "media_comp='" + media_comp + '\'' +
                    ", single='" + single + '\'' +
                    ", songName='" + songName + '\'' +
                    ", coverUrl='" + coverUrl + '\'' +
                    ", isSupportFavorite=" + isSupportFavorite +
                    ", isFavorite=" + isFavorite +
                    ", isPlay=" + isPlay +
                    ", likeEnable=" + likeEnable +
                    '}';
        }
    }
}
