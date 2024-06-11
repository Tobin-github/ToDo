package top.tobin.todo.service

import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import top.tobin.common.eventbus.observeEvent
import top.tobin.my.event.TestEvent

class TestService : LifecycleService() {

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    override fun onCreate() {
        super.onCreate()

        observeEvent<TestEvent>(lifecycleScope) {

        }

    }
}