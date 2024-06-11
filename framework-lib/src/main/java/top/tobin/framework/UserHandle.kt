package top.tobin.framework

import android.annotation.SuppressLint
import android.os.UserHandle

object UserHandle {
    const val USER_SYSTEM = UserHandle.USER_SYSTEM


    @SuppressLint("NewApi")
    fun getUserHandleForUid(uid: Int) {
        UserHandle.getUserHandleForUid(uid)

    }

    fun getSystemUserHandle(): UserHandle {
        return UserHandle.of(USER_SYSTEM)
    }
}