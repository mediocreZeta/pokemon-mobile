package com.zeta.pokemonapp.page.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zeta.pokemonapp.R
import com.zeta.pokemonapp.core.domain.model.PokemonModel
import com.zeta.pokemonapp.databinding.ItemPokemonBinding

class PagingHomePokemonAdapter(val onClickCard: (PokemonModel) -> Unit) :
    PagingDataAdapter<PokemonModel, PagingHomePokemonAdapter.PagingViewHolder>(UserComparator) {

    class PagingViewHolder(
        val binding: ItemPokemonBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val context = itemView.context

        fun bind(pokemon: PokemonModel) {
            with(binding) {
                tvPokemonId.text = pokemon.id.toString()
                tvPokemonName.text = pokemon.name
                Glide.with(context)
                    .load(pokemon.image)
                    .error(AppCompatResources.getDrawable(context, R.drawable.ic_broken_image))
                    .into(imgPokemon)
            }
        }
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        val pokemon = getItem(position) ?: return
        holder.bind(pokemon)
        holder.itemView.setOnClickListener {
            onClickCard(pokemon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        val binding = ItemPokemonBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PagingViewHolder(binding)
    }


    object UserComparator : DiffUtil.ItemCallback<PokemonModel>() {
        override fun areItemsTheSame(oldItem: PokemonModel, newItem: PokemonModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PokemonModel, newItem: PokemonModel): Boolean {
            return oldItem == newItem
        }
    }
}