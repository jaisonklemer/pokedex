package com.klemer.pokedexapp.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.klemer.pokedexapp.R
import com.klemer.pokedexapp.adapters.GenBottomModalAdapter
import com.klemer.pokedexapp.databinding.MainActivityBinding
import com.klemer.pokedexapp.view.fragments.MainFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var bottomSheetView: View
    private lateinit var bottomSheetRecyclerView: RecyclerView
    private lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        loadFilterComponents()

        findViewById<ImageView>(R.id.imgGenerationFilter).setOnClickListener {
            bottomSheetDialog.show()
        }
    }

    fun loadFilterComponents() {
        bottomSheetView = View.inflate(this, R.layout.bottom_modal_generations, null)
        bottomSheetRecyclerView =
            bottomSheetView.findViewById(R.id.recyclerViewGenerations)

        bottomSheetRecyclerView.adapter = GenBottomModalAdapter(){
            println("Generation item $it")
        }
        bottomSheetRecyclerView.layoutManager = GridLayoutManager(this, 2)

        /*
        * Bottom Sheet Dialog for Generations Filter
        * */
        bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(bottomSheetView)
    }

}