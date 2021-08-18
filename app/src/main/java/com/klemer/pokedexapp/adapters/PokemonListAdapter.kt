package com.klemer.pokedexapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener
import com.google.android.material.card.MaterialCardView
import com.klemer.pokedexapp.R
import com.klemer.pokedexapp.enums.PokemonImageEnum
import com.klemer.pokedexapp.enums.TypeColorEnum
import com.klemer.pokedexapp.models.PokemonListItem
import com.klemer.pokedexapp.models.Types
import java.util.*
import android.graphics.drawable.PictureDrawable
import android.os.Handler
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target


class PokemonListAdapter() :
    RecyclerView.Adapter<PokemonListViewHolder>() {
    private var pokemonList: MutableList<PokemonListItem> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.pokemon_list_item, parent, false)
        return PokemonListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        holder.bind(pokemonList[position], position)
    }

    override fun getItemCount() = pokemonList.size

    fun updateList(newList: MutableList<PokemonListItem>) {
        pokemonList.addAll(newList)
        notifyDataSetChanged()
    }

}

class PokemonListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @SuppressLint("ResourceType")
    fun bind(pokemon: PokemonListItem, position: Int) {
        val typesSize = pokemon.types.size
        val type1 = TypeColorEnum.valueOf(
            pokemon.types[0].type.typeName.uppercase(
                Locale.getDefault()
            )
        )

        itemView.findViewById<MaterialCardView>(R.id.layoutType1).apply {
            setCardBackgroundColor(Color.parseColor(type1.typeBadgeColor))
        }

        itemView.findViewById<MaterialCardView>(R.id.pokemonCard).apply {
            setCardBackgroundColor(
                Color.parseColor(
                    type1.bgColor
                )
            )
        }

        itemView.findViewById<TextView>(R.id.textViewType1).apply {
            text = type1.toString().lowercase().capitalize()
        }

        itemView.findViewById<ImageView>(R.id.imageType1).apply {
            setImageResource(
                TypeColorEnum.valueOf(
                    pokemon.types[0].type.typeName.uppercase(
                        Locale.getDefault()
                    )
                ).icon
            )
        }

        if (typesSize > 1) {

            val type2 = TypeColorEnum.valueOf(
                pokemon.types[1].type.typeName.uppercase(
                    Locale.getDefault()
                )
            )

            itemView.findViewById<MaterialCardView>(R.id.layoutType2).apply {
                setCardBackgroundColor(Color.parseColor(type2.typeBadgeColor))
                visibility = View.VISIBLE
            }

            itemView.findViewById<ImageView>(R.id.imageType2).apply {
                setImageResource(type2.icon)
            }

            itemView.findViewById<TextView>(R.id.textViewType2).apply {
                text = type2.toString().lowercase().capitalize()
            }
        }

        itemView.findViewById<TextView>(R.id.txtViewPokemonName).apply {
            text = pokemon.name.capitalize()
        }

        itemView.findViewById<TextView>(R.id.txtViewPokemonId).apply {
            "#${pokemon.id}".also { text = it }
        }

        itemView.findViewById<ImageView>(R.id.pokemonImageView).apply {
            val svgImageUrl = "${PokemonImageEnum.SVG.url}${pokemon.id}.svg"
            loadImageUrl(svgImageUrl, this, pokemon.id, context)
        }
    }

    private fun loadImageUrl(
        imageUrl: String,
        imageView: ImageView,
        pokemonId: Int,
        context: Context
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
                    isFirstResource: Boolean
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
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

            })
            .into(imageView)


    }
}