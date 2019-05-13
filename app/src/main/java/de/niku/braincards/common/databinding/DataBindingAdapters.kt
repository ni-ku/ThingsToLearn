package de.niku.braincards.common.databinding

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

class DataBindingAdapters {

    companion object {
        @JvmStatic @BindingAdapter("errorText")
        fun setErrorText(til: TextInputLayout, text: String?) {
            til.error = text
        }
    }
}