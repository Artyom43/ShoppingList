package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopItemActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishListener {

    private var screenMode = UNKNOWN_MODE
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()

        if (savedInstanceState == null) {                                       //  иначе активити пересоздалась, и фрагмент в ней уже существует
            val fragment = when (screenMode) {
                EDIT_MODE -> ShopItemFragment.newInstanceEditItem(shopItemId)
                ADD_MODE -> ShopItemFragment.newInstanceAddItem()
                else -> throw RuntimeException("Unknown screen mode $screenMode")
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.shop_item_container, fragment)
                .commit()
        }
    }

    override fun onEditingFinished() {
        finish()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen is absent")
        }
        intent.getStringExtra(EXTRA_SCREEN_MODE).let { mode ->
            if (mode != EDIT_MODE && mode != ADD_MODE) {
                throw RuntimeException("Unknown screen mode $mode")
            }
            screenMode = mode
            if (mode == EDIT_MODE) {
                if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                    throw RuntimeException("Param shop item id is absent")
                }
                shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
            }
        }
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val EDIT_MODE = "edit_mode"
        private const val ADD_MODE = "add_mode"
        private const val UNKNOWN_MODE = ""

        fun newIntentAddItem(context: Context): Intent {
            return Intent(context, ShopItemActivity::class.java)
                .putExtra(EXTRA_SCREEN_MODE, ADD_MODE)
        }

        fun newIntentEditItem(context: Context, itemId: Int): Intent {
            return Intent(context, ShopItemActivity::class.java)
                .putExtra(EXTRA_SCREEN_MODE, EDIT_MODE)
                .putExtra(EXTRA_SHOP_ITEM_ID, itemId)
        }
    }
}