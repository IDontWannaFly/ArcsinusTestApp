package com.mark.arcsinustestapp.adapters

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

abstract class RecyclerViewAdapterBase<T, V : ViewDataBinding> : RecyclerView.Adapter<ViewWrapper<V>>() {
    open var items: List<T> = ArrayList()
    var listener: ItemClickListener<T>? = null

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewWrapper<V> {
        return ViewWrapper(onCreateItemView(parent, viewType))
    }

    protected abstract fun onCreateItemView(parent: ViewGroup, viewType: Int): V

    fun interface ItemClickListener<T>{
        fun onClick(item: T, position: Int)
    }
}