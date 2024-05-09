package com.zeta.pokemonapp.util

import android.app.Activity
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputLayout

class RenameReleasePokemonDialog(private val activity: Activity) {
    fun createDialog(
        onPositiveClick: () -> Unit = {},
        onNegativeClick: () -> Unit = {},
        sectionTitle: String,
        isSuccess: Boolean
    ) {
        val title = "$sectionTitle status"
        val message = when (isSuccess) {
            true -> "Successfully $sectionTitle pokemon."
            false -> "Failed to $sectionTitle pokemon, please try again."
        }

        val positiveWord = when (isSuccess) {
            true -> "back"
            false -> "Try again"
        }

        val builder = AlertDialog.Builder(activity).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(positiveWord) { _, _ ->
                onPositiveClick()
            }
            if (!isSuccess) {
                setNegativeButton("Back") { _, _ ->
                    onNegativeClick()
                }
            }
            setCancelable(false)
        }
        builder.create()
        builder.show()
    }

}

class CatchPokemonDialog(private val activity: Activity) {
    fun createDialog(
        onPositiveClick: (String) -> Unit = {},
        onNegativeClick: () -> Unit = {},
        initialValue: String
    ) {
        val textInputLayout = TextInputLayout(activity)
        textInputLayout.setPadding(53, 0, 53, 0)
        val input = EditText(activity)
        input.setText(initialValue)
        textInputLayout.addView(input)

        val alert = AlertDialog.Builder(activity).apply {
            setTitle("Name your pokemon")
            setView(textInputLayout)
            setPositiveButton("Save") { dialog, _ ->
                val text = input.text.toString()
                if (text.isNotEmpty()) {
                    onPositiveClick(text)
                    dialog.cancel()
                }
            }
            setNegativeButton("Cancel") { dialog, _ ->
                onNegativeClick()
                dialog.cancel()
            }
            setCancelable(false)
        }.create()
        alert.show()
    }


}

