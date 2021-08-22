package com.klemer.pokedexapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.PictureDrawable
import android.os.Handler
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.google.android.material.card.MaterialCardView
import com.klemer.pokedexapp.R
import com.klemer.pokedexapp.enums.PokemonImageEnum
import com.klemer.pokedexapp.enums.TypeColorEnum

class BindingComponentsUtil(private var holder: View) {
    fun setText(textMessage: String, @IdRes component: Int) {
        holder.findViewById<TextView>(component).apply {
            text = textMessage
        }
    }

    fun <T : View?> setVisibility(@IdRes component: Int, visible: Boolean) {
        holder.findViewById<T>(component)?.apply {
            visibility = if (visible) View.VISIBLE else View.GONE
        }
    }

    fun setImageResource(@IdRes imgView: Int, @DrawableRes imageResource: Int) {
        holder.findViewById<ImageView>(imgView).apply {
            setImageResource(imageResource)
        }
    }

    fun setPokemonImage(@IdRes imgView: Int, imageId: Int) {
        val svgImageUrl = "${PokemonImageEnum.SVG.url}${imageId}.svg"

        holder.findViewById<ImageView>(imgView).apply {
            loadImageFromUrl(svgImageUrl, this, imageId, context)
        }
    }

    fun setCardBackgroundColor(color: String, @IdRes componentId: Int) {
        holder.findViewById<MaterialCardView>(componentId).apply {
            setCardBackgroundColor(Color.parseColor(color))
        }
    }

    fun setLayoutBackgroundColor(color: String, @IdRes componentId: Int) {
        holder.findViewById<View>(componentId).apply {
            setBackgroundColor(Color.parseColor(color))
        }
    }

    /**
     * Function to configure design for Pokemon Type Badges
     *
     * */
    @SuppressLint("ResourceType")
    fun configureBadgeType(
        @IdRes layoutId: Int,
        pokemonType: TypeColorEnum?,
        @IdRes typeTextView: Int?,
        @IdRes typeImageView: Int?,
        changeBgColor: Boolean,
        visible: Boolean,
    ) {

        if (pokemonType != null) {

            this.setText(pokemonType.toString().lowercase().capitalize(), typeTextView!!)
            this.setImageResource(typeImageView!!, pokemonType.icon)
            this.setCardBackgroundColor(pokemonType.typeBadgeColor, layoutId)

            if (changeBgColor) {
                this.setCardBackgroundColor(pokemonType.bgColor, R.id.pokemonCard)
            }
        }

        if (visible) {
            this.setVisibility<MaterialCardView>(layoutId,
                true)
        } else {
            this.setVisibility<MaterialCardView>(layoutId, false)
        }
    }

    private fun loadImageFromUrl(
        imageUrl: String,
        imageView: ImageView,
        pokemonId: Int,
        context: Context,
    ) {
        val pngImageUrl = "${PokemonImageEnum.PNG.url}$pokemonId.png"

        val requestBuilder = GlideToVectorYou
            .init()
            .with(context)
            .requestBuilder

        requestBuilder
            .load(imageUrl)
            .listener(object : RequestListener<PictureDrawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<PictureDrawable>?,
                    isFirstResource: Boolean,
                ): Boolean {

                    Handler().postDelayed(Runnable {
                        Glide
                            .with(context)
                            .load(pngImageUrl)
                            .into(imageView)
                    }, 100)

                    return false
                }

                override fun onResourceReady(
                    resource: PictureDrawable?,
                    model: Any?,
                    target: Target<PictureDrawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean,
                ): Boolean {
                    return false
                }

            })
            .into(imageView)
    }
}