package com.example.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.shoppinglist.data.ShopListRepoImpl
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopListUseCase
import com.example.shoppinglist.domain.RemoveShopItemUseCase
import com.example.shoppinglist.domain.ShopItem

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = ShopListRepoImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repo)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repo)
    private val editShopItemUseCase = EditShopItemUseCase(repo)

    val shopList = getShopListUseCase.getShopList()

    fun removeShopItem(shopItem: ShopItem) {
        removeShopItemUseCase.removeShopItem(shopItem)
    }

    fun changeEnabledState(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newItem)
    }
}