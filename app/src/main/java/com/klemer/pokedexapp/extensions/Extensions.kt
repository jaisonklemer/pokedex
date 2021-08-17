package com.klemer.pokedexapp.extensions

import kotlin.reflect.KMutableProperty

fun Any.setPropertyValue(propName: String, value: Any) {
    for (prop in this::class.members) {
        if (prop.name == propName) {
            (prop as? KMutableProperty<*>)?.setter?.call(this, value)
        }
    }
}