package top.tobin.media

object VideoConstant {

    fun getVideoList(): MutableList<String> {
        val test = mutableListOf<String>()
        test.add("https://vfx.mtime.cn/Video/2021/07/10/mp4/210710094507540173.mp4")
        test.add("http://demo-videos.qnsdk.com/bbk-H265-50fps.mp4")
        test.add("https://stream7.iqilu.com/10339/upload_transcode/202002/09/20200209105011F0zPoYzHry.mp4")
        return test
    }
}