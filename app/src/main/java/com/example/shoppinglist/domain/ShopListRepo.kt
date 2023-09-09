package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepo {

    fun addShopItem(shopItem: ShopItem)
    fun editShopItem(shopItem: ShopItem)
    fun getShopItem(id: Int): ShopItem
    fun removeShopItem(shopItem: ShopItem)

    fun getShopList(): LiveData<List<ShopItem>>
}