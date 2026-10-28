package com.outivox.myabc.ui.presentation.setting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.outivox.myabc.databinding.ItemSettingMenuBinding
import com.outivox.myabc.databinding.ItemSettingSectionHeaderBinding
import com.outivox.myabc.ui.organisms.MenuResourceItemAttribute
import com.outivox.myabc.ui.organisms.SettingMenuSectionAttribute

sealed class SettingMenuListItem {
    data class Header(val title: String) : SettingMenuListItem()
    data class Item(val data: MenuResourceItemAttribute) : SettingMenuListItem()
}

class SettingMenuAdapter(
    private val onItemClick: (MenuResourceItemAttribute) -> Unit = {},
) : ListAdapter<SettingMenuListItem, RecyclerView.ViewHolder>(DiffCallback) {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1

        private val DiffCallback = object : DiffUtil.ItemCallback<SettingMenuListItem>() {
            override fun areItemsTheSame(oldItem: SettingMenuListItem, newItem: SettingMenuListItem): Boolean {
                return when {
                    oldItem is SettingMenuListItem.Header && newItem is SettingMenuListItem.Header ->
                        oldItem.title == newItem.title
                    oldItem is SettingMenuListItem.Item && newItem is SettingMenuListItem.Item ->
                        oldItem.data.label == newItem.data.label
                    else -> false
                }
            }

            override fun areContentsTheSame(oldItem: SettingMenuListItem, newItem: SettingMenuListItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SettingMenuListItem.Header -> VIEW_TYPE_HEADER
            is SettingMenuListItem.Item -> VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val binding = ItemSettingSectionHeaderBinding.inflate(inflater, parent, false)
                HeaderViewHolder(binding)
            }
            VIEW_TYPE_ITEM -> {
                val binding = ItemSettingMenuBinding.inflate(inflater, parent, false)
                ItemViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Unknown viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is SettingMenuListItem.Header -> (holder as HeaderViewHolder).bind(item)
            is SettingMenuListItem.Item -> (holder as ItemViewHolder).bind(item.data, onItemClick)
        }
    }

    @JvmName("submitSectionList")
    fun submitList(sections: List<SettingMenuSectionAttribute>?) {
        val flatList = sections?.flatMap { section ->
            listOf(SettingMenuListItem.Header(section.title)) +
                section.itemList.map { SettingMenuListItem.Item(it) }
        }
        super.submitList(flatList)
    }

    class HeaderViewHolder(
        private val binding: ItemSettingSectionHeaderBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(header: SettingMenuListItem.Header) {
            binding.tvSectionTitle.text = header.title
        }
    }

    class ItemViewHolder(
        private val binding: ItemSettingMenuBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: MenuResourceItemAttribute,
            onItemClick: (MenuResourceItemAttribute) -> Unit,
        ) {
            binding.ivMenuIcon.setImageResource(item.iconRes)
            binding.tvMenuLabel.text = item.label
            if (!item.description.isNullOrEmpty()) {
                binding.tvMenuDesc.text = item.description
                binding.tvMenuDesc.visibility = View.VISIBLE
            } else {
                binding.tvMenuDesc.visibility = View.GONE
            }
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}
