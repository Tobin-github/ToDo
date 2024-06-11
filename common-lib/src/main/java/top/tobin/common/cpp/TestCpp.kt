package top.tobin.common.cpp

object TestCpp {

    /**
     * A native method that is implemented by the 'libtest' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    // Used to load the 'myapplication' library on application startup.
    init {
        System.loadLibrary("libtest")
    }
}