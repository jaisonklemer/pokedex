package com.klemer.pokedexapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.klemer.pokedexapp.R
import com.klemer.pokedexapp.enums.TypeColorEnum
import java.util.*
import com.klemer.pokedexapp.interfaces.PokemonClickListener
import com.klemer.pokedexapp.models.PokemonItem
import com.klemer.pokedexapp.utils.BindingComponentsUtil


class PokemonListAdapter(private val clickListener: PokemonClickListener) :
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
        holder.itemView.setOnClickListener {
            clickListener.onPokemonClick(pokemonList[position].id)
        }
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

    private val bindingUtil = BindingComponentsUtil(itemView)

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

        bindingUtil.setText(pokemon.name.capitalize(), R.id.txtViewPokemonName)
        bindingUtil.setText("#${pokemon.id}", R.id.txtViewPokemonId)
        bindingUtil.setPokemonImage(R.id.pokemonImageView, pokemon.id.toInt())

        bindingUtil.configureBadgeType(
            R.id.cardBadgeLayout,
            pokemonType1,
            R.id.pokemonTypeName,
            R.id.pokemonTypeIcon,
            true,
            visible = true)

        if (typesSize > 1) {

            val pokemonType2 = TypeColorEnum.valueOf(
                pokemon.types!![1].type.typeName.uppercase(
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