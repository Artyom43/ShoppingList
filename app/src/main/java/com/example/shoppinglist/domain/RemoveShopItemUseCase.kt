package com.example.shoppinglist.domain

class RemoveShopItemUseCase(private val shopListRepo: ShopListRepo) {

    suspend fun removeShopItem(shopItem: ShopItem) {
        shopListRepo.removeShopItem(shopItem)
    }
}