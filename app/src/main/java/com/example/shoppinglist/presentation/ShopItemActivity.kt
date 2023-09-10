package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var buttonSave: Button

    private var screenMode = UNKNOWN_MODE
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews()

        setupInputTextChangeListeners()
        observeViewModel()
        when (screenMode) {
            EDIT_MODE -> launchEditMode()
            ADD_MODE -> launchAddMode()
        }
    }

    private fun launchEditMode() {
        buttonSave.setOnClickListener {
            viewModel.editShopItem(etName.text.toString(), etCount.text.toString())
        }

        viewModel.shopItem.observe(this) {
            etName.text.insert(etName.selectionStart, it.name)
            etCount.text.insert(etCount.selectionStart, it.count.toString())
        }
        viewModel.getShopItem(shopItemId)
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            viewModel.addShopItem(etName.text.toString(), etCount.text.toString())
        }
    }

    private fun setupInputTextChangeListeners() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun afterTextChanged(p0: Editable?) = Unit

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }
        })

        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun afterTextChanged(p0: Editable?) = Unit

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }
        })
    }

    private fun observeViewModel() {
        viewModel.errorInputName.observe(this) {
            tilName.error = when (it) {
                true -> getString(R.string.error_input_name)
                false -> null
            }
        }
        viewModel.errorInputCount.observe(this) {
            tilCount.error = when (it) {
                true -> getString(R.string.error_input_count)
                false -> null
            }
        }
        viewModel.shouldCloseScreen.observe(this) {
            finish()
        }
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

    private fun initViews() {
        tilName = findViewById(R.id.til_name)
        tilCount = findViewById(R.id.til_count)
        etName = findViewById(R.id.et_name)
        etCount = findViewById(R.id.et_count)
        buttonSave = findViewById(R.id.save_button)
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