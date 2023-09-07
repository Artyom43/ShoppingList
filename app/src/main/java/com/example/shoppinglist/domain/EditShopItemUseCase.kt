package com.example.shoppinglist.domain

class EditShopItemUseCase(private val shopListRepo: ShopListRepo) {

    fun editShopItem(shopItem: ShopItem) {
        shopListRepo.editShopItem(shopItem)
    }
}