package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ItemShopDisabledBinding
import com.example.shoppinglist.databinding.ItemShopEnabledBinding
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            ENABLED -> R.layout.item_shop_enabled
            DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ShopItemViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).enabled) ENABLED else DISABLED
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = holder.binding
        when (getItemViewType(position)) {
            ENABLED -> {
                (binding as ItemShopEnabledBinding).apply {
                    tvName.text = shopItem.name
                    tvCount.text = shopItem.count.toString()
                }
            }

            DISABLED -> {
                (binding as ItemShopDisabledBinding).apply {
                    tvName.text = shopItem.name
                    tvCount.text = shopItem.count.toString()
                }
            }
        }
        binding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        binding.root.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
    }

    companion object {
        const val ENABLED = 0
        const val DISABLED = 1

        const val MAX_POOL_SIZE = 5
    }
}
