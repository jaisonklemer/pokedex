package com.klemer.pokedexapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.klemer.pokedexapp.R
import com.klemer.pokedexapp.enums.GenerationsFilterEnum
import com.klemer.pokedexapp.singletons.GenerationsList

class GenBottomModalAdapter(val filterClick: (Int) -> Unit) :
    RecyclerView.Adapter<GenBottomModalViewHolder>() {
    private var currentItem = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenBottomModalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bottom_modal_item, parent, false)
        return GenBottomModalViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: GenBottomModalViewHolder,
        @SuppressLint("RecyclerView") position: Int,
    ) {
        holder.bind(GenerationsList.get[position], position, currentItem)

        holder.itemView.setOnClickListener {
            currentItem = position
            filterClick(position)
            notifyDataSetChanged()
        }

    }

    override fun getItemCount() = GenerationsList.get.size
}

class GenBottomModalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var card = itemView.findViewById<MaterialCardView>(R.id.firstGenCar)
    var txtTitle = itemView.findViewById<TextView>(R.id.textViewGenerationName)

    fun bind(generation: GenerationsFilterEnum, position: Int, current: Int) {

        if (current == position) {
            card.apply {
                background.setTint(getResources().getColor(R.color.psychic_color))
            }

            txtTitle.apply {
                setTextColor(getResources().getColor(R.color.white))
            }
        } else {
            card.apply {
                background.setTint(getResources().getColor(R.color.generation_bg_color))
            }

            txtTitle.apply {
                setTextColor(getResources().getColor(R.color.text_color_gray))
            }
        }


        itemView.findViewById<ImageView>(R.id.firstIconGen).apply {
            setImageResource(generation.icon1)
        }

        itemView.findViewById<ImageView>(R.id.secondIconGen).apply {
            setImageResource(generation.icon2)
        }

        itemView.findViewById<ImageView>(R.id.thirdGenCarIcon).apply {
            setImageResource(generation.icon3)
        }

        txtTitle.apply {
            text = generation.title
        }
    }

}