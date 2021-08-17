package com.klemer.pokedexapp.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.klemer.pokedexapp.R
import com.klemer.pokedexapp.enums.PokemonImageEnum
import com.klemer.pokedexapp.models.PokemonListItem

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

    fun bind(pokemon: PokemonListItem, position: Int) {

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
}