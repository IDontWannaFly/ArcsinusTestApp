package com.mark.arcsinustestapp.adapters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class ViewWrapper<V : ViewDataBinding>(val binding: V) : RecyclerView.ViewHolder(binding.root)