package com.klemer.pokedexapp.view.fragments

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView.OnEditorActionListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.klemer.pokedexapp.R
import com.klemer.pokedexapp.adapters.PokemonListAdapter
import com.klemer.pokedexapp.databinding.MainFragmentBinding
import com.klemer.pokedexapp.view_model.MainViewModel
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.klemer.pokedexapp.adapters.GenBottomModalAdapter
import com.klemer.pokedexapp.extensions.hideKeyboard
import com.klemer.pokedexapp.extensions.showToast
import com.klemer.pokedexapp.models.PokemonResponse
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
    private lateinit var bottomSheetView: View
    private lateinit var bottomSheetRecyclerView: RecyclerView
    private lateinit var bottomSheetDialog: BottomSheetDialog


    private val observerPrimaryPokemonList = Observer<PokemonResponse> {
        viewModel.treatPokemonList(it, requireContext())
    }

    private val observerFinalPokemonList = Observer<PokemonResponse?> {
        showProgress(false)

        if (it != null) {
            adapter.updateList(it.pokemons, false)
        } else {
            adapter.updateList(null, true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadFilterComponents()
        openBottomSheet()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Reset API offset count when the fragment is created*/
        APICount.offsetCount = 0

        binding = MainFragmentBinding.bind(view)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.primaryPokemonList.observe(viewLifecycleOwner, observerPrimaryPokemonList)
        viewModel.finalPokemonsList.observe(viewLifecycleOwner, observerFinalPokemonList)

        linearLayoutManager = LinearLayoutManager(requireContext())

        bindViewComponents()
        getPokemons()
        addRecyclerViewScrollListener()
        bindSearch()
    }

    private fun bindViewComponents() {
        adapter = PokemonListAdapter()
        binding.recyclerViewPokemons.adapter = adapter
        binding.recyclerViewPokemons.layoutManager = linearLayoutManager
    }

    private fun addRecyclerViewScrollListener() {
        binding.recyclerViewPokemons.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) &&
                    newState == RecyclerView.SCROLL_STATE_IDLE &&
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

            val querySearch: String = textEditSearch.text.toString().lowercase()

            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    if (querySearch.isNotEmpty()) {
                        showProgress(true)
                        viewModel.searchPokemon(querySearch) { pokemonItem, error ->
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
        viewModel.fetchAllFromDatabase(requireContext())
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            binding.loadingProgressBar.visibility = View.VISIBLE
        } else {
            binding.loadingProgressBar.visibility = View.GONE
        }
    }

    private fun loadFilterComponents() {
        bottomSheetView = View.inflate(requireContext(), R.layout.bottom_modal_generations, null)
        bottomSheetRecyclerView =
            bottomSheetView.findViewById(R.id.recyclerViewGenerations)

        /**
         * Bottom Sheet Dialog for Generations Filter
         * */
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bottomSheetView)

        bottomSheetRecyclerView.adapter = GenBottomModalAdapter { itemPosition ->
            val generationId = itemPosition + 1

            if (APICount.genFilterSelected != generationId) {
                /**
                 * Dismiss dialog
                 * */
                bottomSheetDialog.hide()
                viewModel.clearPokemonList()
                showProgress(true)
                viewModel.getPokemonFromGeneration(generationId.toString()) {
                    APICount.genFilterSelected = generationId
                }
            }
        }
        bottomSheetRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

    }

    private fun openBottomSheet() {
        requireActivity().findViewById<ImageView>(R.id.imgGenerationFilter).setOnClickListener {
            bottomSheetDialog.show()
        }
    }

}