package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

class GetShopListUseCase(private val shopListRepo: ShopListRepo) {

    fun getShopList(): LiveData<List<ShopItem>> = shopListRepo.getShopList()
}