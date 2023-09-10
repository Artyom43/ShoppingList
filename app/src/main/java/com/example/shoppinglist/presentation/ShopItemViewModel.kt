package com.example.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepoImpl
import com.example.shoppinglist.domain.AddShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopItemUseCase
import com.example.shoppinglist.domain.ShopItem
import java.lang.NumberFormatException

class ShopItemViewModel : ViewModel() {

    private val repo = ShopListRepoImpl

    private val getShopItemUseCase = GetShopItemUseCase(repo)
    private val addShopItemUseCase = AddShopItemUseCase(repo)
    private val editShopItemUseCase = EditShopItemUseCase(repo)

    private val _errorInputName = MutableLiveData<Boolean>()
    private val _errorInputCount = MutableLiveData<Boolean>()
    private val _shopItem = MutableLiveData<ShopItem>()
    private val _shouldCloseScreen = MutableLiveData<Unit>()

    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getShopItem(id: Int) {
        val item = getShopItemUseCase.getShopItem(id)
        _shopItem.value = item
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        if (validateInput(name, count)) {
            _shopItem.value?.let {
                val shopItem = it.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(shopItem)
                finishWork()
            }
        }
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        if (validateInput(name, count)) {
            val shopItem = ShopItem(name = name, count = count, enabled = true)
            addShopItemUseCase.addShopItem(shopItem)
            finishWork()
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (nfe: NumberFormatException) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}