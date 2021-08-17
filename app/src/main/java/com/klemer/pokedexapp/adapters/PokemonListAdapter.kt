package com.klemer.pokedexapp.adapters

import android.annotation.SuppressLint
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
import com.google.android.material.card.MaterialCardView
import com.klemer.pokedexapp.R
import com.klemer.pokedexapp.enums.PokemonImageEnum
import com.klemer.pokedexapp.enums.TypeColorEnum
import com.klemer.pokedexapp.models.PokemonListItem
import com.klemer.pokedexapp.models.Types
import java.util.*

class PokemonListAdapter(val pokemonList: List<PokemonListItem>) :
    RecyclerView.Adapter<PokemonListViewHolder>() {
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
            text = "#${position + 1}"
        }

        itemView.findViewById<ImageView>(R.id.pokemonImageView).apply {
            val imageUrl = PokemonImageEnum.IMAGE.url + (position + 1) + ".svg"

            GlideToVectorYou
                .init()
                .with(context)
                .load(Uri.parse(imageUrl), this)

        }
    }

    private fun enumTypes(types: List<Types>) {

    }
}