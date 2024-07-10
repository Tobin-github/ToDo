package top.tobin.web

import android.os.Bundle
import android.text.TextUtils
import android.webkit.CookieManager
import top.tobin.web.basefragment.BaseWebViewFragment
import top.tobin.web.utils.WebConstants
import java.util.HashMap

/**
 * Created by Tobin on 2022/5/31.
 * Email: junbin.li@qq.com
 * Description: web容器.
 */
class CommonWebFragment : BaseWebViewFragment() {
    override val layoutRes: Int get() = R.layout.fragment_common_webview

    companion object {
        fun newInstance(
            url: String,
            headers: HashMap<String, String> = HashMap(),
            isSyncToCookie: Boolean = false
        ): CommonWebFragment {
            val fragment = CommonWebFragment()
            fragment.arguments = getBundle(url, headers)
            if (isSyncToCookie) {
                syncCookie(url, headers)
            }
            return fragment
        }

        private fun getBundle(url: String, headers: HashMap<String, String>): Bundle {
            val bundle = Bundle()
            bundle.putString(WebConstants.INTENT_TAG_URL, url)
            bundle.putSerializable(ACCOUNT_INFO_HEADERS, headers)
            return bundle
        }

        /**
         * 将cookie同步到WebView
         *
         * @param url WebView要加载的url
         * @return true 同步cookie成功，false同步cookie失败
         */
        private fun syncCookie(url: String?, map: Map<String, String>): Boolean {
            val cookieManager = CookieManager.getInstance()
            for (key in map.keys) {
                cookieManager.setCookie(url, key + "=" + map[key])
            }
            val newCookie = cookieManager.getCookie(url)
            return !TextUtils.isEmpty(newCookie)
        }
    }


}