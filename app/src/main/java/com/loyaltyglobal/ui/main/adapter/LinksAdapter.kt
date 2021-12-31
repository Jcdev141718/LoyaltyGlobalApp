package com.loyaltyglobal.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyglobal.R
import com.loyaltyglobal.databinding.ItemLinkBinding
import com.loyaltyglobal.util.clickWithDebounce

/**
 * Created by Abhin.
 */
class LinksAdapter(private val mList: ArrayList<String>, private val listener: LinkClickListeners) :
    RecyclerView.Adapter<LinksAdapter.LinksVH>() {

    interface LinkClickListeners {
        fun onClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinksVH {
        return LinksVH(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_link, parent, false))
    }

    override fun getItemCount() = mList.size

    override fun onBindViewHolder(holder: LinksVH, position: Int) {
        holder.bind(mList[position])
        holder.itemView.clickWithDebounce { listener.onClick(position) }
    }

    class LinksVH(val binding: ItemLinkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) = binding.apply {
            link = item
            executePendingBindings()
        }
    }
}