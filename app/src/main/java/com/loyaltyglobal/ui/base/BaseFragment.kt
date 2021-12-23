package com.loyaltyglobal.ui.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.loyaltyglobal.ui.main.view.activity.MainActivity
import com.loyaltyglobal.util.PreferenceProvider
import com.loyaltyglobal.util.hide
import com.loyaltyglobal.util.hideKeyboard
import com.loyaltyglobal.util.show
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Abhin.
 */

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    var mBaseActivity: BaseActivity? = null
    protected val mPreferenceProvider: PreferenceProvider?
        get() = mBaseActivity?.mPreferenceProvider

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) mBaseActivity = context
    }

    fun hideBottomNavigation() {
        try {
            (activity as MainActivity).binding.menuBottom.hide()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showBottomNavigation() {
        try {
            (activity as MainActivity).binding.menuBottom.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        activity?.hideKeyboard()
        super.onDestroyView()
    }
}
