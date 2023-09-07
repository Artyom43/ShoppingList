package com.example.shoppinglist.domain

interface ShopListRepo {

    fun addShopItem(shopItem: ShopItem)
    fun editShopItem(shopItem: ShopItem)
    fun getShopItem(id: Int): ShopItem
    fun removeShopItem(shopItem: ShopItem)

    fun getShopList(): List<ShopItem>
}