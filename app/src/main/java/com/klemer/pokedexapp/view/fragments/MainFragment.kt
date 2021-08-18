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
import androidx.recyclerview.widget.RecyclerView


class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: PokemonListAdapter

    private val observerPokemons = Observer<PokemonList> {
        viewModel.treatPokemonList(it)
    }

    private val observerPokemonList = Observer<PokemonList> {
        adapter.updateList(it.pokemons)
        binding.recyclerViewPokemons.layoutManager = linearLayoutManager
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = MainFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        linearLayoutManager = LinearLayoutManager(requireContext())

        adapter = PokemonListAdapter()
        binding.recyclerViewPokemons.adapter = adapter

        viewModel.pokemons.observe(viewLifecycleOwner, observerPokemons)
        viewModel.pokemonsList.observe(viewLifecycleOwner, observerPokemonList)

        getPokemons()

        binding.recyclerViewPokemons.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    getPokemons()
                }
            }
        })
    }

    private fun getPokemons() {
        viewModel.getPokemons()
    }

}