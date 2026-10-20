package com.outivox.myabc.ui.presentation.setting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.outivox.myabc.databinding.ItemSettingMenuBinding
import com.outivox.myabc.databinding.ItemSettingSectionHeaderBinding
import com.outivox.myabc.ui.organisms.MenuResourceItemAttribute
import com.outivox.myabc.ui.organisms.SettingMenuSectionAttribute

sealed interface SettingListItem {
    data class SectionHeader(val title: String) : SettingListItem
    data class MenuItem(val attribute: MenuResourceItemAttribute) : SettingListItem
}

/**
 * SettingMenuAdapter: Legacy XML RecyclerView Adapter demonstrating sectioned item rendering.
 *
 * In Step 2 of the migration strategy, this Adapter and its associated XML ViewHolders
 * are replaced by ComposeView + SettingMenuSection organisms, and finally pure LazyColumn in Step 3.
 */
class SettingMenuAdapter(
    private val onClickItem: ((MenuResourceItemAttribute) -> Unit)? = null,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<SettingListItem> = emptyList()
    private var currentSections: List<SettingMenuSectionAttribute>? = null

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    fun submitList(sections: List<SettingMenuSectionAttribute>) {
        if (currentSections == sections) return
        currentSections = sections

        val flatList = mutableListOf<SettingListItem>()
        sections.forEach { section ->
            flatList.add(SettingListItem.SectionHeader(section.title))
            section.itemList.forEach { item ->
                flatList.add(SettingListItem.MenuItem(item))
            }
        }
        items = flatList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is SettingListItem.SectionHeader -> VIEW_TYPE_HEADER
            is SettingListItem.MenuItem -> VIEW_TYPE_ITEM
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
                MenuViewHolder(binding, onClickItem)
            }
            else -> throw IllegalArgumentException("Unknown viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is SettingListItem.SectionHeader -> (holder as HeaderViewHolder).bind(item)
            is SettingListItem.MenuItem -> (holder as MenuViewHolder).bind(item)
        }
    }

    class HeaderViewHolder(
        private val binding: ItemSettingSectionHeaderBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(header: SettingListItem.SectionHeader) {
            binding.tvSectionTitle.text = header.title
        }
    }

    class MenuViewHolder(
        private val binding: ItemSettingMenuBinding,
        private val onClickItem: ((MenuResourceItemAttribute) -> Unit)?,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menuItem: SettingListItem.MenuItem) {
            val item = menuItem.attribute
            binding.ivMenuIcon.setImageResource(item.iconRes)
            binding.tvMenuLabel.text = item.label
            if (!item.description.isNullOrBlank()) {
                binding.tvMenuDescription.text = item.description
                binding.tvMenuDescription.visibility = View.VISIBLE
            } else {
                binding.tvMenuDescription.visibility = View.GONE
            }
            binding.root.setOnClickListener {
                onClickItem?.invoke(item)
            }
        }
    }
}
