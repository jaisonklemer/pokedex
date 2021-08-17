package com.klemer.pokedexapp.view.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.klemer.pokedexapp.R
import com.klemer.pokedexapp.adapters.PokemonListAdapter
import com.klemer.pokedexapp.databinding.MainFragmentBinding
import com.klemer.pokedexapp.models.PokemonList
import com.klemer.pokedexapp.view_model.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    private val observerPokemons = Observer<PokemonList> {
        viewModel.treatPokemonList(it)
    }

    private val observerPokemonList = Observer<PokemonList> {
        binding.recyclerViewPokemons.adapter = PokemonListAdapter(it.pokemons)
        binding.recyclerViewPokemons.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = MainFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.pokemons.observe(viewLifecycleOwner, observerPokemons)
        viewModel.pokemonsList.observe(viewLifecycleOwner, observerPokemonList)

        viewModel.getPokemons()
    }

}