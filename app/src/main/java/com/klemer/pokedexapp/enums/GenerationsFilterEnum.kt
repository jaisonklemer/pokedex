package com.klemer.pokedexapp.enums

import androidx.annotation.DrawableRes
import com.klemer.pokedexapp.R

enum class GenerationsFilterEnum(
    val title: String,
    @DrawableRes val icon1: Int,
    @DrawableRes val icon2: Int,
    @DrawableRes val icon3: Int,
) {
    FIRST("Generation I",
        R.drawable.ic_generation_1_001,
        R.drawable.ic_generation_1_002,
        R.drawable.ic_generation_1_003),

    SECOND("Generation II",
        R.drawable.ic_generation_2_001,
        R.drawable.ic_generation_2_002,
        R.drawable.ic_generation_2_003),

    THIRD("Generation III",
        R.drawable.ic_generation_3_001,
        R.drawable.ic_generation_3_002,
        R.drawable.ic_generation_3_003),

    FOURTH("Generation IV",
        R.drawable.ic_generation_4_001,
        R.drawable.ic_generation_4_002,
        R.drawable.ic_generation_4_003),

    FIFTH("Generation V",
        R.drawable.ic_generation_5_001,
        R.drawable.ic_generation_5_002,
        R.drawable.ic_generation_5_003),

    SIXTH("Generation VI",
        R.drawable.ic_generation_6_001,
        R.drawable.ic_generation_6_002,
        R.drawable.ic_generation_6_003),

    SEVENTH("Generation VII",
        R.drawable.ic_generation_7_001,
        R.drawable.ic_generation_7_002,
        R.drawable.ic_generation_7_003),

    EIGHTH("Generation VIII",
        R.drawable.ic_generation_8_001,
        R.drawable.ic_generation_8_002,
        R.drawable.ic_generation_8_003),
}

object GENERATIONS {
    val toList = listOf(
        GenerationsFilterEnum.FIRST,
        GenerationsFilterEnum.SECOND,
        GenerationsFilterEnum.THIRD,
        GenerationsFilterEnum.FOURTH,
        GenerationsFilterEnum.FIFTH,
        GenerationsFilterEnum.SIXTH,
        GenerationsFilterEnum.SEVENTH,
        GenerationsFilterEnum.EIGHTH,
    )
}

