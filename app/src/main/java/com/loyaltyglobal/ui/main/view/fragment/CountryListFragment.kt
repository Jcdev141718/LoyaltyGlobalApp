package com.loyaltyglobal.ui.main.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.loyaltyglobal.data.model.CountryCodeData
import com.loyaltyglobal.databinding.FragmentCountryListBinding
import com.loyaltyglobal.ui.main.adapter.CountryAdapter
import com.loyaltyglobal.util.hideKeyboard
import com.loyaltyglobal.ui.main.viewmodel.CountryListViewModel


class CountryListFragment : Fragment() {

    private lateinit var mBinding : FragmentCountryListBinding
    private val mCountryListViewModel: CountryListViewModel by viewModels()
    var mCountryAdapter: CountryAdapter? = null
    var mCountryList = ArrayList<CountryCodeData>()

    var mSendCountryCodeAndFlag: SendCountryCodeAndFlag? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserver()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initObserver() {
        mCountryListViewModel.countryList.observe(this, {
            if (!it.isNullOrEmpty()) {
                mCountryList.clear()
                mCountryList.addAll(it)
                mCountryAdapter?.notifyDataSetChanged()
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = FragmentCountryListBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCountryListViewModel.getCountryList()
        setCountryAdapter()
        clickListener()
    }


    private fun clickListener() {
        mBinding.edtContactSearch.doOnTextChanged { text, _, _, _ ->
            mCountryAdapter?.filter?.filter(text.toString())
        }

        mBinding.imgClear.setOnClickListener {
            activity?.hideKeyboard()
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun setCountryAdapter() {
        mCountryAdapter = CountryAdapter(mCountryList, object : CountryAdapter.CountryCodeListener {
            override fun countryCodeListener(mCountryCodeData: CountryCodeData) {
                mSendCountryCodeAndFlag?.onCountryDataSelect(mCountryCodeData)
                activity?.supportFragmentManager?.popBackStack()
            }
        })
        mBinding.rvCountryList.layoutManager = LinearLayoutManager(activity)
        mBinding.rvCountryList.adapter = mCountryAdapter
    }
}
