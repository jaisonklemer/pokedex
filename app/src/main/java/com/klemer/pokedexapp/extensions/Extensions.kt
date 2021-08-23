package com.klemer.pokedexapp.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun Context.hideKeyboard(view: View) {
    val inputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.replaceFragment(
    fragmentManager: FragmentManager,
    fragment: Fragment,
    @IdRes container: Int,
    backStack: Boolean,
) {
    val fm = fragmentManager
        .beginTransaction()
        .replace(container, fragment)
    if (backStack) fm.addToBackStack(null)

    fm.commit()
}