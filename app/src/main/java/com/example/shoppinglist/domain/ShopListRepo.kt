package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepo {

    suspend fun addShopItem(shopItem: ShopItem)
    suspend fun editShopItem(shopItem: ShopItem)
    suspend fun getShopItem(id: Int): ShopItem
    suspend fun removeShopItem(shopItem: ShopItem)

    fun getShopList(): LiveData<List<ShopItem>>
}