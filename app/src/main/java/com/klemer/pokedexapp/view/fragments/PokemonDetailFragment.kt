package com.klemer.pokedexapp.view.fragments

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.klemer.pokedexapp.R
import com.klemer.pokedexapp.databinding.PokemonDetailFragmentBinding
import com.klemer.pokedexapp.enums.PokemonImageEnum
import com.klemer.pokedexapp.enums.TypeColorEnum
import com.klemer.pokedexapp.models.PokemonItem
import com.klemer.pokedexapp.utils.BindingComponentsUtil
import com.klemer.pokedexapp.view_model.PokemonDetailViewModel
import java.util.*

class PokemonDetailFragment : Fragment(R.layout.pokemon_detail_fragment) {

    companion object {
        fun newInstance() = PokemonDetailFragment()
    }

    private lateinit var viewModel: PokemonDetailViewModel
    private lateinit var binding: PokemonDetailFragmentBinding
    private lateinit var pokemonId: String
    private lateinit var bindingUtil: BindingComponentsUtil

    private val observerPokemon = Observer<PokemonItem> {
        bindPokemonDetail(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemonId = arguments?.getString("pokemonId").toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PokemonDetailFragmentBinding.bind(view)
        bindingUtil = BindingComponentsUtil(view)
        viewModel = ViewModelProvider(this)[PokemonDetailViewModel::class.java]
        viewModel.pokemonDetailLive.observe(viewLifecycleOwner, observerPokemon)

        getPokemonDetail(pokemonId)
    }

    private fun getPokemonDetail(id: String) {
        viewModel.getPokemonDetail(id)
    }

    fun bindPokemonDetail(pokemon: PokemonItem) {

        val pokemonType1 = TypeColorEnum.valueOf(
            pokemon.types[0].type.typeName.uppercase(
                Locale.getDefault()
            )
        )
        bindingUtil.setLayoutBackgroundColor(pokemonType1.bgColor, binding.headerDetailLayout.id)
        bindingUtil.setPokemonImage(binding.pokemonImageView.id, pokemon.id)
        bindingUtil.setText("#${pokemon.id}", binding.txtViewPokemonId.id)
        bindingUtil.setText(pokemon.name.capitalize(), binding.txtViewPokemonName.id)

        bindingUtil.configureBadgeType(
            binding.pokemonTypeInclude.cardBadgeLayout.id,
            pokemonType1,
            binding.pokemonTypeInclude.pokemonTypeName.id,
            binding.pokemonTypeInclude.pokemonTypeIcon.id,
            false,
            visible = true)

        if (pokemon.types.size > 1) {
            val pokemonType2 = TypeColorEnum.valueOf(
                pokemon.types[1].type.typeName.uppercase(
                    Locale.getDefault()
                )
            )

            bindingUtil.configureBadgeType(
                binding.pokemonTypeInclude.cardBadgeLayout2.id,
                pokemonType2,
                binding.pokemonTypeInclude.pokemonTypeName2.id,
                binding.pokemonTypeInclude.pokemonTypeIcon2.id,
                false,
                visible = true)
        } else {
            bindingUtil.setVisibility<MaterialCardView>(binding.pokemonTypeInclude.cardBadgeLayout2.id,
                false)
        }
    }
}