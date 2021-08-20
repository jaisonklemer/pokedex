package com.klemer.pokedexapp.view.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView.OnEditorActionListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.klemer.pokedexapp.R
import com.klemer.pokedexapp.adapters.PokemonListAdapter
import com.klemer.pokedexapp.databinding.MainFragmentBinding
import com.klemer.pokedexapp.models.PokemonList
import com.klemer.pokedexapp.view_model.MainViewModel
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.klemer.pokedexapp.extensions.hideKeyboard
import com.klemer.pokedexapp.extensions.showToast
import com.klemer.pokedexapp.singletons.APICount
import com.klemer.pokedexapp.view.activities.MainActivity


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

    private val observerPokemonList = Observer<PokemonList?> {
        showProgress(false)

        if (it != null) {
            adapter.updateList(it.pokemons, false)
        } else {
            adapter.updateList(null, true)
        }
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
        viewModel.finalPokemonsList.observe(viewLifecycleOwner, observerPokemonList)

        getPokemons()
        addRecyclerViewScrollListener()
        bindSearch()
    }

    fun addRecyclerViewScrollListener() {
        binding.recyclerViewPokemons.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE &&
                    !APICount.isSearch
                ) {
                    getPokemons()
                }
            }
        })
    }

    private fun bindSearch() {
        val act = requireActivity() as? MainActivity
        val textEditSearch = act?.findViewById<TextInputEditText>(R.id.textEditSearch)

        textEditSearch?.setOnEditorActionListener(OnEditorActionListener { view, actionId, _ ->
            viewModel.clearPokemonList()
            requireContext().hideKeyboard(view)

            val querySearch: String = textEditSearch.text.toString()

            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    if (querySearch.isNotEmpty()) {
                        showProgress(true)
                        viewModel.searchPokemon(querySearch) { pokemonItem, error ->
                            println(pokemonItem)
                            println(error)
                            if (pokemonItem == null) {
                                requireContext().showToast(getString(R.string.no_pokemon_found))
                                showProgress(false)
                            }
                            APICount.isSearch = true
                        }
                    } else {
                        APICount.offsetCount = 0
                        APICount.isSearch = false
                        getPokemons()
                    }
                }
            }
            true
        })
    }

    private fun getPokemons() {
        showProgress(true)
        viewModel.getPokemons()
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            binding.loadingProgressBar.visibility = View.VISIBLE
        } else {
            binding.loadingProgressBar.visibility = View.GONE
        }
    }

}