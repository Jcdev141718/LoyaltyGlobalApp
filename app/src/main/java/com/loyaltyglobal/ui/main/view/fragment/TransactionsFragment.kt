package com.loyaltyglobal.ui.main.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.loyaltyglobal.data.source.network.ApiResponseStates
import com.loyaltyglobal.data.source.network.manageApiDataByState
import com.loyaltyglobal.databinding.FragmentTransactionsBinding
import com.loyaltyglobal.ui.base.BaseFragment
import com.loyaltyglobal.ui.main.adapter.TransactionAdapter
import com.loyaltyglobal.ui.main.viewmodel.ExploreViewModel
import com.loyaltyglobal.util.hide
import com.loyaltyglobal.util.show
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Abhin.
 */
@AndroidEntryPoint
class TransactionsFragment : BaseFragment() {

    lateinit var mBinding: FragmentTransactionsBinding
    private val exploreViewModel: ExploreViewModel by activityViewModels()
    private val adapterTransaction = TransactionAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentTransactionsBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exploreViewModel.getTransactionData()
        clickListener()
        initObserver()
        setAdapter()

    }

    private fun clickListener() {
        mBinding.imgTransactionBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun setAdapter() {
        mBinding.apply {
            rvTransaction.layoutManager =  LinearLayoutManager(requireContext())
            rvTransaction.adapter = adapterTransaction
        }

    }


    private fun initObserver() {
        mBinding.apply {
            exploreViewModel.transactionData.observe(viewLifecycleOwner,{
                if (it != null) {
                    manageApiDataByState(it, object : ApiResponseStates {
                        override fun onSuccess(responseData: Any?) {
                            progressbar.hide()
                            adapterTransaction.submitList(it.responseData?.data)
                            rvTransaction.show()
                        }
                        override fun onLoading() {
                            progressbar.show()
                        }
                        override fun onError(codeData: Int?, message: String?) {
                            progressbar.hide()
                        }
                    })
                }

            })

            /*exploreViewModel.transactionList.observe(viewLifecycleOwner,{
                adapterTransaction.submitList(it)
            })*/
        }

    }
}
