package top.tobin.media

import android.icu.text.DateFormat
import android.icu.text.DecimalFormat
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.text.format.Formatter
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import top.tobin.common.viewbinding.binding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import top.tobin.common.cpp.TestCpp
import top.tobin.media.databinding.FragmentMediaBinding
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class MediaFragment : Fragment(R.layout.fragment_media) {

    private val binding: FragmentMediaBinding by binding()
    private val viewModel: MediaViewModel by viewModels()
    private var mCountDown: Job? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val testStr = TestCpp.stringFromJNI()

        val format = DecimalFormat("#%")
        val result = format.format(50L.toDouble() / 10020L.toDouble())
        val size = Formatter.formatShortFileSize(context, 88802).dropLast(1)

        val calendar = Calendar.getInstance()
        val curHour = calendar.get(Calendar.HOUR_OF_DAY)
        val curMinute = calendar.get(Calendar.MINUTE)

        //"MM-dd HH:mm:ss"
        val time1 = SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.getDefault()).format(calendar.time)
        val time2 = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.time)
        val time21 = DateFormat.getTimeInstance(DateFormat.MEDIUM).format(calendar.time)


//        binding.tvTest.text =
//            "多媒体$testStr $result  $size time: ${calendar.time} curHour: $curHour minute: $curMinute time1: $time1"

        binding.tvTest.text = "$time2 $time21"
    }

    companion object {

        @JvmStatic
        fun newInstance() = MediaFragment()

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MediaFragment().apply {
                arguments = Bundle().apply {
                    putString("ARG_PARAM1", param1)
                    putString("ARG_PARAM2", param2)
                }
            }
    }
}


