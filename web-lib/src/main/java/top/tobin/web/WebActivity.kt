package top.tobin.web

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import top.tobin.common.utils.LogUtil
import top.tobin.web.basefragment.BaseWebViewFragment
import top.tobin.web.command.base.Command
import top.tobin.web.command.base.ResultBack
import top.tobin.web.command.webviewprocess.WebViewProcessCommandsManager
import top.tobin.web.utils.WebConstants

/**
 * Created by Tobin on 2022/5/31.
 * Email: junbin.li@qq.com
 * Description: webview容器.
 */
class WebActivity : AppCompatActivity() {
    private lateinit var tvTitle: TextView
    private lateinit var ivClose: ImageView
    private lateinit var ivBack: ImageView
    private lateinit var webViewFragment: BaseWebViewFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_web)
        ivClose = findViewById(R.id.iv_web_close)
        ivBack = findViewById(R.id.iv_web_back)
        tvTitle = findViewById(R.id.tv_web_title)

        val title = intent?.getStringExtra(WebConstants.INTENT_TAG_TITLE)
        if (!title.isNullOrEmpty()) tvTitle.text = title

        val url = intent?.getStringExtra(WebConstants.INTENT_TAG_URL) ?: ""
        val header =
            intent?.extras?.getSerializable(WebConstants.INTENT_TAG_HEADERS) as? HashMap<String, String>
                ?: HashMap()

        webViewFragment = CommonWebFragment.newInstance(url, header)
        supportFragmentManager.beginTransaction().replace(R.id.web_view_fragment, webViewFragment)
            .commitNowAllowingStateLoss()

        WebViewProcessCommandsManager.instance.registerCommand(titleUpdateCommand)

        ivBack.setOnClickListener {
            if (!webViewFragment.onBackPressed()) {
                finish()
            }
        }

        ivClose.setOnClickListener {
            finish()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val flag = webViewFragment.onKeyDown(keyCode, event)
        if (flag) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        LogUtil.d("WebActivity", "onBackPressed")
    }

    /**
     * 页面路由, 更新标题为html标题
     */
    private var titleUpdateCommand: Command = object : Command {
        override fun name(): String {
            return Command.COMMAND_UPDATE_TITLE
        }

        override fun exec(
            context: Context?, params: MutableMap<String, Any>, resultBack: ResultBack
        ) {
            if (params.containsKey(Command.COMMAND_UPDATE_TITLE_PARAMS)) {
                tvTitle.text = params[Command.COMMAND_UPDATE_TITLE_PARAMS] as String?
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        webViewFragment.onDestroy()
    }

    companion object {
        /**
         * 打开一个网页
         */
        fun startCommonWeb(
            context: Context,
            url: String,
            title: String? = null,
            headers: HashMap<String, String>? = null
        ) {
            val intent = Intent(context, WebActivity::class.java)
            val bundle = Bundle().apply {
                putString(WebConstants.INTENT_TAG_TITLE, title)
                putString(WebConstants.INTENT_TAG_URL, url)
                putSerializable(WebConstants.INTENT_TAG_HEADERS, headers)
            }
            if (context is Service) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            intent.putExtras(bundle)
            context.startActivity(intent)
        }

    }
}