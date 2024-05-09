package com.zeta.pokemonapp.util

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar


fun MaterialToolbar.removeAllReference() {
    this.setOnMenuItemClickListener(null)
    this.menu.clear()
}

fun RecyclerView.removeAllReference() {
    this.layoutManager = null
    this.adapter = null
}

