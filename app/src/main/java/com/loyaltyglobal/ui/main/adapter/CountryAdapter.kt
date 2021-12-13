package com.loyaltyglobal.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyglobal.R
import com.loyaltyglobal.data.model.CountryCodeData
import com.loyaltyglobal.databinding.CountryListBinding
import java.util.*
import kotlin.collections.ArrayList


@SuppressLint("SetTextI18n")
class CountryAdapter(var mCountryCodeList: ArrayList<CountryCodeData>, var mCountryCodeListener: CountryCodeListener? = null) : RecyclerView.Adapter<CountryAdapter.CountryListViewHolder>(), Filterable {

    var mCountryListBinding: CountryListBinding? = null
    private var mFilterList: ArrayList<CountryCodeData> = ArrayList()

    init {
        mFilterList = mCountryCodeList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryListViewHolder {
        mCountryListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_country_code_list, parent, false)
        return CountryListViewHolder(mCountryListBinding!!)
    }

    override fun getItemCount(): Int {
        return mFilterList.size
    }

    override fun onBindViewHolder(holder: CountryListViewHolder, position: Int) {
        holder.bindData(mFilterList[position])
        holder.itemView.setOnClickListener {
            mCountryCodeListener?.countryCodeListener(mFilterList[position])
        }
    }

    class CountryListViewHolder(var mCountryListBinding: CountryListBinding) : RecyclerView.ViewHolder(mCountryListBinding.root) {
        fun bindData(countryCodeData: CountryCodeData) = mCountryListBinding.apply {
            mCountryList = countryCodeData
            executePendingBindings()
        }
    }

    interface CountryCodeListener {
        fun countryCodeListener(mCountryCodeData: CountryCodeData)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charString: String = charSequence.toString()
                mFilterList = if (charString.isEmpty()) {
                    mCountryCodeList
                } else {
                    val filteredList: ArrayList<CountryCodeData> = ArrayList()
                    for (row in mCountryCodeList) {
                        if (row.countryName?.lowercase(Locale.getDefault())!!.contains(charString.lowercase(
                                Locale.getDefault()
                            ))) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = mFilterList
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
                mFilterList = filterResults?.values as ArrayList<CountryCodeData>
                notifyDataSetChanged()
            }
        }
    }
}
