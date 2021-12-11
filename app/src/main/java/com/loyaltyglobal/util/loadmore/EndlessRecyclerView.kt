package com.loyaltyglobal.util.loadmore

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Abhin.
 */
class EndlessRecyclerView (context: Context, attributeSet: AttributeSet) : RecyclerView(context, attributeSet) {
    private var lastPage:Boolean = false
    private var blockLoad:Boolean = false
    private var loadBeforeBottom = false

    //Offset for load before bottom
    var loadOffset = 3

    //Set default layout for recycler view to be linear
    init {
        layoutManager = LinearLayoutManager(context)
    }

    //THIS IS LAMBDA VERSION SINCE THIS IS NOT WRITTEN IN JAVA SO SAM CONVERSION IS NOT APPLICABLE
    //Set callback for the endless scroll view, this function must be called else the endless scroll view will not work
    fun setEndlessScrollCallback(callback: ()-> Unit) {

        //Scroll listener for the recycler view
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val pos = layoutManager.findLastCompletelyVisibleItemPosition()
                val numItems = recyclerView.adapter?.itemCount

                //Initialize default offset
                var offset = 3
                //If recycler view is set to load before reaching bottom
                //Then set the offset value to the number when it want to load before reaching bottom of the list
                if(loadBeforeBottom)
                    offset = loadOffset

                if(!blockLoad && !lastPage && pos >= numItems!! - offset) {
                    callback.invoke()
                }
            }
        })
    }

    //Do this when you don't want to load data anymore
    fun setLastPage() {
        lastPage = true
    }

    //Block load more from being called, usually used when waiting for API call to finish
    fun blockLoading() {
        blockLoad = true
    }

    //Unblock load more, usaually used when API call has finished
    fun releaseBlock() {
        blockLoad = false
    }

    //For loading data before reaching bottom
    fun setLoadBeforeBottom(boolean: Boolean) {
        this.loadBeforeBottom = boolean
    }
}