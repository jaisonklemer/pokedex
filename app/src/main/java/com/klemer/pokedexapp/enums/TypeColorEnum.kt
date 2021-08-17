package com.klemer.pokedexapp.enums

import android.graphics.drawable.Drawable
import androidx.annotation.LayoutRes
import com.klemer.pokedexapp.R

enum class TypeColorEnum(
    val bgColor: String,
    val typeBadgeColor: String,
    @LayoutRes val icon: Int
) {
    BUG("#8BD674", "#8CB230", R.drawable.ic_bug),
    DARK("#6F6E78", "#58575F", R.drawable.ic_dark),
    DRAGON("#7383B9", "#0F6AC0", R.drawable.ic_dragon),
    ELECTRIC("#F2CB55", "#EED535", R.drawable.ic_electric),
    FAIRY("#EBA8C3", "#ED6EC7", R.drawable.ic_fairy),
    FIGHTING("#EB4971", "#D04164", R.drawable.ic_fighting),
    FIRE("#FFA756", "#FD7D24", R.drawable.ic_fire),
    FLYING("#83A2E3", "#748FC9", R.drawable.ic_flying),
    GHOST("#8571BE", "#556AAE", R.drawable.ic_ghost),
    GRASS("#8BBE8A", "#62B957", R.drawable.ic_grass),
    GROUND("#F78551", "#DD7748", R.drawable.ic_ground),
    ICE("#91D8DF", "#61CEC0", R.drawable.ic_ice),
    NORMAL("#B5B9C4", "#9DA0AA", R.drawable.ic_normal),
    POISON("#9F6E97", "#A552CC", R.drawable.ic_poison),
    PSYCHIC("#FF6568", "#EA5D60", R.drawable.ic_psychic),
    ROCK("#D4C294", "#BAAB82", R.drawable.ic_rock),
    STEEL("#4C91B2", "#417D9A", R.drawable.ic_steel),
    WATER("#58ABF6", "#4A90DA", R.drawable.ic_water)

}