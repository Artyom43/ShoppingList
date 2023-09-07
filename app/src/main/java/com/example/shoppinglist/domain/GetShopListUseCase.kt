package com.example.shoppinglist.domain

class GetShopListUseCase(private val shopListRepo: ShopListRepo) {

    fun getShopList(): List<ShopItem> = shopListRepo.getShopList()
}