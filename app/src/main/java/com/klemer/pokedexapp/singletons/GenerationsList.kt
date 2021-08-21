package com.klemer.pokedexapp.singletons

import com.klemer.pokedexapp.enums.GenerationsFilterEnum

object GenerationsList {
    val get = listOf(
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