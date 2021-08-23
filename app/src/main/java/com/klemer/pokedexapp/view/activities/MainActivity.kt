package com.klemer.pokedexapp.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.klemer.pokedexapp.R
import com.klemer.pokedexapp.databinding.MainActivityBinding
import com.klemer.pokedexapp.view.fragments.MainFragment


class MainActivity : BaseActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}