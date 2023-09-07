package com.example.shoppinglist.domain

class GetShopItemUseCase(private val shopListRepo: ShopListRepo) {

    fun getShopItem(id: Int): ShopItem = shopListRepo.getShopItem(id)
}