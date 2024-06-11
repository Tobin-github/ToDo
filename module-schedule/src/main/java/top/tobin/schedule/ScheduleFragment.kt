package top.tobin.schedule

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import top.tobin.common.viewbinding.binding
import top.tobin.common.utils.LogUtil
import top.tobin.schedule.databinding.FragmentScheduleBinding

@AndroidEntryPoint
class ScheduleFragment : Fragment(R.layout.fragment_schedule) {

    private val binding: FragmentScheduleBinding by binding()
    private val viewModel: ScheduleViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance() = ScheduleFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bubbleSort(intArrayOf(1, 2, 3, 5, 9, 0, 4, 25))

    }

    override fun onDetach() {
        super.onDetach()
    }


    private fun bubbleSort(array: IntArray) {
        for (i in array.indices) {
            LogUtil.d("bubbleSort for i: $i")
        }
    }

}