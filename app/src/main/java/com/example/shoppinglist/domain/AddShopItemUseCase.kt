package com.example.shoppinglist.domain

class AddShopItemUseCase(private val shopListRepo: ShopListRepo) {

    suspend fun addShopItem(shopItem: ShopItem) {
        shopListRepo.addShopItem(shopItem)
    }
}