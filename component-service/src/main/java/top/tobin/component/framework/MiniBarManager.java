package top.tobin.component.framework;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import java.util.Map;

import top.tobin.component.service.IMediaComp;

public class MiniBarManager {
    private static final String TAG = "MiniBarManager";

    private static class Holder {
        private static MiniBarManager INSTANCE = new MiniBarManager();
    }

    public static MiniBarManager getInstance() {
        return Holder.INSTANCE;
    }

    private MiniBarManager() {
    }

    /**
     * 当前加载的组件列表
     */
    private Map<String, IMediaComp> mMediaCompList = new ArrayMap();
    /**
     * 当前处于焦点态的组件
     */
    private String mCurFocusMediaCompName;
    private IMediaComp mCurFocusMediaComp;
    private MinibarUIListener minibarListener;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private IMediaComp.MediaInfo curMediaInfo;

    private boolean checkHasCurFocusMediaComp() {
        if (mCurFocusMediaComp == null) {
            Log.e(TAG, "not set curFocusMediaComp");
            return false;
        }
        return true;
    }

    public void setMinibarListener(MinibarUIListener minibarListener) {
        this.minibarListener = minibarListener;
    }

    /**
     * 注册音源组件
     */
    public int registerMediaComp(IMediaComp comp) {
        if (!mMediaCompList.containsKey(comp.getMediaCompName())) {
            mMediaCompList.put(comp.getMediaCompName(), comp);
        }
        return 0;
    }


    public String getCurFocusMediaCompName() {
        return this.mCurFocusMediaCompName;
    }

    public void clearMediaInfo() {
        mCurFocusMediaCompName = null;
        mCurFocusMediaComp = null;
        curMediaInfo = null;
        if (minibarListener != null) {
            minibarListener.clearMediaInfo();
        }
    }

    /**
     * 刷新当前播放信息,更新播放信息的组件是播放状态时自动成为焦点态组件。
     *
     * @param info media_comp属性必须传递，此播放信息属于哪个组件
     */
    public int refreshMediaInfo(IMediaComp.MediaInfo info) {
        mainHandler.post(() -> {
            Log.i(TAG, "ready refreshMediaInfo:" + info);
            if (info == null) {
                return;
            }
            if (!TextUtils.isEmpty(info.media_comp)) {
                if (TextUtils.isEmpty(mCurFocusMediaCompName) || info.isPlay) {
                    //传递的信息是播放状态时，此组件是焦点态
                    mCurFocusMediaCompName = info.media_comp;
                    mCurFocusMediaComp = mMediaCompList.get(mCurFocusMediaCompName);
                }
            }
            if (TextUtils.isEmpty(info.media_comp) || !info.media_comp.equals(mCurFocusMediaCompName)) {
                Log.e(TAG, "curFocus comp is different from the set comp ,not allow refresh mediainfo!!!!");
                return;
            }
            curMediaInfo = info;
            if (minibarListener != null) {
                minibarListener.refreshMediaInfo(info);
            }
        });
        return 0;
    }

    /**
     * 显示minibar
     *
     * @param fromModule 哪个模块调用的，方便排查问题
     */
    public int notifyShow(String fromModule) {
        mainHandler.post((Runnable) () -> {
            if (minibarListener != null) {
                Log.i(TAG, "notifyShow minibar form:" + fromModule);
                minibarListener.show();
            }
        });

        return 0;
    }

    /**
     * 隐藏minibar
     *
     * @param fromModule 哪个模块调用的，方便排查问题
     */
    public int notifyHide(String fromModule) {
        mainHandler.post((Runnable) () -> {
            if (minibarListener != null) {
                Log.i(TAG, "notifyHide minibar form:" + fromModule);
                minibarListener.hide();
            }
        });

        return 0;
    }

    public boolean minibarIsVisible() {
        if (minibarListener != null) {
            return minibarListener.minibarIsVisible();
        }
        return false;
    }

    public void updatePlayStatus(String media_comp, boolean isPlay) {
        mainHandler.post((Runnable) () -> {
            if (minibarListener != null) {
                minibarListener.updatePlayStatus(media_comp, isPlay);
            }
        });
    }

    public void updateFavoriteStatus(String media_comp, boolean isFavor) {
        mainHandler.post((Runnable) () -> {
            if (minibarListener != null) {
                minibarListener.updateFavoriteStatus(media_comp, isFavor);
            }
        });
    }


    public void play() {
        if (checkHasCurFocusMediaComp()) {
            mCurFocusMediaComp.play();
        }
    }

    public void pause() {
        if (checkHasCurFocusMediaComp()) {
            mCurFocusMediaComp.pause();
        }
    }

    public void next() {
        if (checkHasCurFocusMediaComp()) {
            mCurFocusMediaComp.next();
        }
    }

    public void addFavorite() {
        if (checkHasCurFocusMediaComp()) {
            mCurFocusMediaComp.addFavorite();
        }
    }

    public void removeFavorite() {
        if (checkHasCurFocusMediaComp()) {
            mCurFocusMediaComp.removeFavorite();
        }
    }

    public void jumpToMedia() {
        if (checkHasCurFocusMediaComp()) {
            mCurFocusMediaComp.jumpToMedia();
        }
    }

    public IMediaComp.MediaInfo getMediaInfo() {

        return curMediaInfo;
    }

    public interface MinibarUIListener {
        int show();

        int hide();

        //minibar是否显示
        boolean minibarIsVisible();

        //更新minibar上的播放状态，如果调用该方法的组件 与 minibar当前显示的组件不一致，拒绝更新
        default void updatePlayStatus(String media_comp, boolean isPlay) {

        }

        //更新minibar上的收藏状态，如果调用该方法的组件 与 minibar当前显示的组件不一致，拒绝更新
        default void updateFavoriteStatus(String media_comp, boolean isFavor) {

        }

        default void clearMediaInfo() {

        }

        int refreshMediaInfo(IMediaComp.MediaInfo info);
    }
}
