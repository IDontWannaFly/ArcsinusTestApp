package com.mark.arcsinustestapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mark.arcsinustestapp.databinding.ItemCharacterBinding
import com.mark.arcsinustestapp.models.Character

class CharacterAdapter : RecyclerViewAdapterBase<Character, ItemCharacterBinding>(){
    override fun onCreateItemView(parent: ViewGroup, viewType: Int): ItemCharacterBinding {
        return ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun onBindViewHolder(holder: ViewWrapper<ItemCharacterBinding>, position: Int) {
        val item = items[position]
        holder.binding.item = item
        holder.binding.root.setOnClickListener {
            listener?.onClick(item, position)
        }
    }
}