package com.example.shoppinglist.presentation

import androidx.databinding.BindingAdapter
import com.example.shoppinglist.R
import com.google.android.material.textfield.TextInputLayout

// ShopItemFragment

@BindingAdapter("errorInputName")
fun bindErrorInputName(textInputLayout: TextInputLayout, isError: Boolean) {
    textInputLayout.error = when (isError) {
        true -> textInputLayout.context.getString(R.string.error_input_name)
        false -> null
    }
}

@BindingAdapter("errorInputCount")
fun bindErrorInputCount(textInputLayout: TextInputLayout, isError: Boolean) {
    textInputLayout.error = when (isError) {
        true -> textInputLayout.context.getString(R.string.error_input_count)
        false -> null
    }
}
