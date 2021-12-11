package com.loyaltyglobal.ui.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.loyaltyglobal.util.PreferenceProvider
import com.loyaltyglobal.util.hideKeyboard

/**
 * Created by Abhin.
 */
abstract class BaseFragment : Fragment() {

    var mBaseActivity : BaseActivity? = null
    val mPreferenceProvider : PreferenceProvider?
        get() = mBaseActivity?.mPreferenceProvider

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity)
            mBaseActivity = context
    }

    override fun onDestroyView() {
        activity?.hideKeyboard()
        super.onDestroyView()
    }
}