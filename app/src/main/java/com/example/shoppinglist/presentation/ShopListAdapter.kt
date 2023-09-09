package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val viewId = when (viewType) {
            ENABLED -> R.layout.item_shop_enabled
            DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(viewId, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).enabled) ENABLED else DISABLED
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        with(holder) {
            tvName.text = shopItem.name
            tvCount.text = shopItem.count.toString()
            view.setOnLongClickListener {
                onShopItemLongClickListener?.invoke(shopItem)
                true
            }
            view.setOnClickListener {
                onShopItemClickListener?.invoke(shopItem)
            }
        }
    }

    companion object {
        const val ENABLED = 0
        const val DISABLED = 1

        const val MAX_POOL_SIZE = 5
    }
}
