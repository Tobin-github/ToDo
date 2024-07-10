package top.tobin.web.mainprocess

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException

import top.tobin.web.IBinderPool
import java.util.concurrent.CountDownLatch

/**
 * Created by Tobin on 2022/5/31.
 * Email: junbin.li@qq.com
 * Description: 用于remoteweb process 向 main process 获取binder.
 */
class RemoteWebBinderPool private constructor(context: Context) {
    private val mContext: Context
    private var mBinderPool: IBinderPool? = null
    private var mConnectBinderPoolCountDownLatch: CountDownLatch? = null

    @Synchronized
    private fun connectBinderPoolService() {
        mConnectBinderPoolCountDownLatch = CountDownLatch(1)
        val service = Intent(mContext, MainProHandleRemoteService::class.java)
        mContext.bindService(service, mBinderPoolConnection, Context.BIND_AUTO_CREATE)
        try {
            mConnectBinderPoolCountDownLatch!!.await()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun queryBinder(binderCode: Int): IBinder? {
        var binder: IBinder? = null
        try {
            binder = mBinderPool?.queryBinder(binderCode)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
        return binder
    }

    private val mBinderPoolConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {}
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mBinderPool = IBinderPool.Stub.asInterface(service)
            try {
                mBinderPool?.asBinder()?.linkToDeath(mBinderPoolDeathRecipient, 0)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
            mConnectBinderPoolCountDownLatch!!.countDown()
        }
    }

    private val mBinderPoolDeathRecipient: IBinder.DeathRecipient =
        object : IBinder.DeathRecipient {
            override fun binderDied() {
                mBinderPool?.asBinder()?.unlinkToDeath(this, 0)
                mBinderPool = null
                connectBinderPoolService()
            }
        }

    class BinderPoolImpl(private val context: Context) : IBinderPool.Stub() {
        @Throws(RemoteException::class)
        override fun queryBinder(binderCode: Int): IBinder? {
            var binder: IBinder? = null
            when (binderCode) {
                BINDER_WEB_AIDL -> {
                    binder = MainProAidlInterface(context)
                }
                else -> {}
            }
            return binder
        }
    }

    companion object {
        const val BINDER_WEB_AIDL = 1

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var sInstance: RemoteWebBinderPool? = null
        fun getInstance(context: Context): RemoteWebBinderPool {
            if (sInstance == null) {
                synchronized(RemoteWebBinderPool::class.java) {
                    if (sInstance == null) {
                        sInstance = RemoteWebBinderPool(context)
                    }
                }
            }
            return sInstance!!
        }
    }


    init {
        mContext = context.applicationContext
        connectBinderPoolService()
    }
}