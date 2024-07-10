package top.tobin.web.mainprocess

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Process
import top.tobin.common.utils.LogUtil
import top.tobin.web.mainprocess.RemoteWebBinderPool.BinderPoolImpl

/**
 * Created by Tobin on 2022/5/31.
 * Email: junbin.li@qq.com
 * Description: MainProHandleRemoteService.
 */
class MainProHandleRemoteService : Service() {
    override fun onBind(intent: Intent): IBinder {
        val pid = Process.myPid()
        LogUtil.d(
            "MainProHandleRemoteService",
            "Current process id:$pid client and server are successfully connected," +
                    "and the server returns binderpool.Binderpoolimpl object"
        )
        return BinderPoolImpl(this)
    }
}