package top.tobin.component.eventbus;

public class EventConstants {
    //传递给语音中间件的页面切换信息
    public static final String PAGE_CHANGE_EVENT_KEY = "page_change_event_key";

    public enum PageName {
        //随行听
        MUSIC,
        //推荐
        RECOMMEND,
        //QQ音乐
        QQ_MUSIC,
        //播客
        PODCAST,
        //广播
        RADIO,
        //新闻
        NEWS,
        //蓝牙音乐
        BT_MUSIC,
        //USB音乐
        USB_MUSIC,
        //FM
        FM
    }
}
