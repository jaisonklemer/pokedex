package com.klemer.pokedexapp.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
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



        findViewById<ImageView>(R.id.imgGenerationFilter).setOnClickListener {
            openBottomSheet()
        }
    }

    fun openBottomSheet() {
        val view = View.inflate(this, R.layout.bottom_modal_generations, null)

        val bottom = BottomSheetDialog(this)
        bottom.setContentView(view)
        bottom.show()
    }


}