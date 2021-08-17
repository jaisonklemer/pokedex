package com.klemer.pokedexapp.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.klemer.pokedexapp.R
import com.klemer.pokedexapp.view.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}