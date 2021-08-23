package com.klemer.pokedexapp.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.klemer.pokedexapp.R
import com.klemer.pokedexapp.databinding.ActivityPokemonDetailBinding
import com.klemer.pokedexapp.extensions.replaceFragment
import com.klemer.pokedexapp.view.fragments.PokemonDetailFragment

class PokemonDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPokemonDetailBinding
    private lateinit var pokemonId: String
    private lateinit var fragmentDetail: PokemonDetailFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pokemonId = intent.getStringExtra("pokemonID").toString()
        fragmentDetail = PokemonDetailFragment.newInstance()

        val arguments = Bundle()
        arguments.putString("pokemonId", pokemonId)
        fragmentDetail.arguments = arguments

        replaceFragment(supportFragmentManager, fragmentDetail,
            R.id.container_root,
            false)
    }
}