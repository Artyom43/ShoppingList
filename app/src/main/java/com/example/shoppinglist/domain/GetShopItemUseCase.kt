package com.example.shoppinglist.domain

class GetShopItemUseCase(private val shopListRepo: ShopListRepo) {

    suspend fun getShopItem(id: Int): ShopItem = shopListRepo.getShopItem(id)
}