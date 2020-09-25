package me.oleg.taglibro.framework.presentation.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.setRoundImage(url: String) {

        Glide.with(this)
            .load(url)
            .circleCrop()
            .into(this)
    }
}