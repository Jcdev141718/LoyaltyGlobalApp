package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import com.google.gson.Gson
import com.loyaltyglobal.R
import com.loyaltyglobal.data.model.UpdateProfileEvent
import com.loyaltyglobal.databinding.FragmentDateOfBirthBinding
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.util.Constants
import com.loyaltyglobal.util.clickWithDebounce
import com.loyaltyglobal.util.popFragment
import org.greenrobot.eventbus.EventBus
import java.util.*

/**
 * Created by Abhin.
 */
class DateOfBirthFragment : BaseFragment() {

    private lateinit var binding : FragmentDateOfBirthBinding
    private var isUpdate = false
    private var calendar = Calendar.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDateOfBirthBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.apply {
            val datePickerListener = DatePicker.OnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
                isUpdate = true
                txtNext.isEnabled = true
                calendar.set(year,monthOfYear,dayOfMonth)
                txtNext.setBackgroundResource(R.drawable.shape_filled_button)
                txtDay.text = dayOfMonth.toString()
                txtMonth.text = monthOfYear.plus(1).toString()
                txtYear.text = year.toString()
            }
            mPreferenceProvider?.getUserData()?.let {
                txtWelcome.text = if (!it.firstName.isNullOrEmpty()) {
                    getString(R.string.when_are_you_born_with_name, it.firstName)
                } else {
                    getString(R.string.when_are_you_born)
                }
                val date = Calendar.getInstance().apply { timeInMillis = it.birthday ?: 0 }
                txtDay.text = date[Calendar.DAY_OF_MONTH].toString()
                txtMonth.text = date[Calendar.MONTH].plus(1).toString()
                txtYear.text = date[Calendar.YEAR].toString()
                datePicker.descendantFocusability = DatePicker.FOCUS_BLOCK_DESCENDANTS
                datePicker.init(date[Calendar.YEAR],date[Calendar.MONTH],date[Calendar.DAY_OF_MONTH],datePickerListener)
            }
            txtNext.clickWithDebounce {
                if (isUpdate){
                    val data = mPreferenceProvider?.getUserData()
                    data?.birthday = calendar.timeInMillis
                    mPreferenceProvider?.setValue(Constants.PREF_USER_DATA, Gson().toJson(data))
                }
                EventBus.getDefault().post(UpdateProfileEvent(true))
                activity?.popFragment()
            }
            imgBack.clickWithDebounce { activity?.popFragment() }
        }
    }
}
