package com.example.shoppinglist.domain

class EditShopItemUseCase(private val shopListRepo: ShopListRepo) {

    suspend fun editShopItem(shopItem: ShopItem) {
        shopListRepo.editShopItem(shopItem)
    }
}