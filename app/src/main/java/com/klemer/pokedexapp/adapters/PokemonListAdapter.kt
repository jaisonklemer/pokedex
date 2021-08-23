package com.klemer.pokedexapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.klemer.pokedexapp.R
import com.klemer.pokedexapp.enums.TypeColorEnum
import com.klemer.pokedexapp.models.PokemonListItem
import java.util.*
import com.klemer.pokedexapp.interfaces.PokemonClickListener
import com.klemer.pokedexapp.utils.BindingComponentsUtil


class PokemonListAdapter(val clickListener: PokemonClickListener) :
    RecyclerView.Adapter<PokemonListViewHolder>() {
    private var pokemonList: MutableList<PokemonListItem> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.pokemon_list_item, parent, false)
        return PokemonListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        holder.bind(pokemonList[position])
        holder.itemView.setOnClickListener {
            clickListener.onPokemonClick(pokemonList[position].id.toString())
        }
    }

    override fun getItemCount() = pokemonList.size

    fun updateList(newList: MutableList<PokemonListItem>?, clearList: Boolean) {
        if (clearList) {
            pokemonList = mutableListOf()
        } else {
            pokemonList.addAll(newList!!)
        }
        notifyDataSetChanged()
    }
}

class PokemonListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val bindingUtil = BindingComponentsUtil(itemView)

    @SuppressLint("ResourceType")
    fun bind(pokemon: PokemonListItem) {
        val typesSize = pokemon.types.size

        val pokemonType1 = TypeColorEnum.valueOf(
            pokemon.types[0].type.typeName.uppercase(
                Locale.getDefault()
            )
        )

        bindingUtil.setText(pokemon.name.capitalize(), R.id.txtViewPokemonName)
        bindingUtil.setText("#${pokemon.id}", R.id.txtViewPokemonId)
        bindingUtil.setPokemonImage(R.id.pokemonImageView, pokemon.id)

        bindingUtil.configureBadgeType(
            R.id.cardBadgeLayout,
            pokemonType1,
            R.id.pokemonTypeName,
            R.id.pokemonTypeIcon,
            true,
            visible = true)

        if (typesSize > 1) {

            val pokemonType2 = TypeColorEnum.valueOf(
                pokemon.types[1].type.typeName.uppercase(
                    Locale.getDefault()
                )
            )

            bindingUtil.configureBadgeType(
                layoutId = R.id.cardBadgeLayout2,
                pokemonType = pokemonType2,
                R.id.pokemonTypeName2,
                R.id.pokemonTypeIcon2,
                changeBgColor = false,
                visible = true)

        } else {
            bindingUtil.setVisibility<MaterialCardView>(R.id.cardBadgeLayout2,
                false)
        }
    }

}