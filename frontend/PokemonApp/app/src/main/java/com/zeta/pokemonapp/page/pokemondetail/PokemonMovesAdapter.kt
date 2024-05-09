package com.zeta.pokemonapp.page.pokemondetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeta.pokemonapp.databinding.ItemMovesBinding

class PokemonMovesAdapter : ListAdapter<String, PokemonMovesAdapter.MovesViewHolder>(
    FavoriteDiffCallback
) {

    class MovesViewHolder(
        private val binding: ItemMovesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(move: String) {
            with(binding) {
                tvPokemonMove.text = move
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovesViewHolder {
        val binding =
            ItemMovesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovesViewHolder, position: Int) {
        val move = currentList[position]
        holder.bind(move)
    }

    private object FavoriteDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }
    }
}