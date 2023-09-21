package com.example.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepo

class ShopListRepoImpl(
    application: Application
) : ShopListRepo {

    private val shopListDao = AppDataBase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()

    override fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override fun editShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override fun getShopItem(id: Int): ShopItem {
        val dbModel = shopListDao.getShopItem(id)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun removeShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }


    override fun getShopList(): LiveData<List<ShopItem>> = shopListDao.getShopList().map {
        mapper.mapListDbModelToListEntity(it)
    }

//           realization of the function above, MediatorLiveData - important!

//    override fun getShopList(): LiveData<List<ShopItem>> =
//        MediatorLiveData<List<ShopItem>>().apply {
//            addSource(shopListDao.getShopList()) {
//                value = mapper.mapListDbModelToListEntity(it)
//            }
//        }
}