package com.klemer.pokedexapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.google.android.material.card.MaterialCardView
import com.klemer.pokedexapp.R
import com.klemer.pokedexapp.enums.PokemonImageEnum
import com.klemer.pokedexapp.enums.TypeColorEnum
import java.util.*
import android.graphics.drawable.PictureDrawable
import android.os.Handler
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.klemer.pokedexapp.models.PokemonItem


class PokemonListAdapter :
    RecyclerView.Adapter<PokemonListViewHolder>() {
    private var pokemonList: MutableList<PokemonItem> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.pokemon_list_item, parent, false)
        return PokemonListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        holder.bind(pokemonList[position])
    }

    override fun getItemCount() = pokemonList.size

    fun updateList(newList: MutableList<PokemonItem>?, clearList: Boolean) {
        if (clearList) {
            pokemonList = mutableListOf()
        } else {
            pokemonList.addAll(newList!!)
        }
        notifyDataSetChanged()
    }


}

class PokemonListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @SuppressLint("ResourceType")
    fun bind(pokemon: PokemonItem) {
        val typesSize = pokemon.types.size

        val pokemonType1 = pokemon.types.get(0).type.typeName.let {
            TypeColorEnum.valueOf(
                it.uppercase(
                    Locale.getDefault()
                )
            )
        }

        setText(pokemon.name.capitalize(), R.id.txtViewPokemonName)
        setText(pokemonType1.toString().lowercase().capitalize(), R.id.textViewType1)
        setText("#${pokemon.id}", R.id.txtViewPokemonId)
        setCardBackgroundColor(pokemonType1.typeBadgeColor, R.id.layoutType1)
        setCardBackgroundColor(pokemonType1.bgColor, R.id.pokemonCard)
        setImageResource(R.id.imageType1, pokemonType1.icon)
        setPokemonImage(R.id.pokemonImageView, pokemon.id.toInt())

        if (typesSize > 1) {

            val pokemonType2 = TypeColorEnum.valueOf(
                pokemon.types!![1].type.typeName.uppercase(
                    Locale.getDefault()
                )
            )
            setText(pokemonType2.toString().lowercase().capitalize(), R.id.textViewType2)
            setCardBackgroundColor(pokemonType2.typeBadgeColor, R.id.layoutType2)
            setImageResource(R.id.imageType2, pokemonType2.icon)
            setVisibility<MaterialCardView>(R.id.layoutType2, true)

        } else {
            setVisibility<MaterialCardView>(R.id.layoutType2, false)
        }
    }

    private fun setText(textMessage: String, @IdRes component: Int) {
        itemView.findViewById<TextView>(component).apply {
            text = textMessage
        }
    }

    private fun <T : View?> setVisibility(@IdRes component: Int, visible: Boolean) {
        itemView.findViewById<T>(component)?.apply {
            visibility = if (visible) View.VISIBLE else View.GONE
        }
    }

    private fun setImageResource(@IdRes imgView: Int, @DrawableRes imageResource: Int) {
        itemView.findViewById<ImageView>(imgView).apply {
            setImageResource(imageResource)
        }
    }

    private fun setPokemonImage(@IdRes imgView: Int, imageId: Int) {
        val svgImageUrl = "${PokemonImageEnum.SVG.url}${imageId}.svg"

        itemView.findViewById<ImageView>(imgView).apply {
            loadImageFromUrl(svgImageUrl, this, imageId, context)
        }
    }

    private fun setCardBackgroundColor(color: String, @IdRes componentId: Int) {
        itemView.findViewById<MaterialCardView>(componentId).apply {
            setCardBackgroundColor(Color.parseColor(color))
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