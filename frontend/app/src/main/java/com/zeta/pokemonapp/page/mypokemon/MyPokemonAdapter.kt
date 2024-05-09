package com.zeta.pokemonapp.page.mypokemon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zeta.pokemonapp.R
import com.zeta.pokemonapp.core.domain.model.PokemonWithDetailsModel
import com.zeta.pokemonapp.databinding.ItemPokemonBinding

class MyPokemonAdapter(
    private val onClick: (PokemonWithDetailsModel) -> Unit
) : ListAdapter<PokemonWithDetailsModel, MyPokemonAdapter.MyPokemonViewHolder>(FavoriteDiffCallback) {

    class MyPokemonViewHolder(
        private val binding: ItemPokemonBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val context = itemView.context
        fun bind(pokemon: PokemonWithDetailsModel) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPokemonViewHolder {
        val binding =
            ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyPokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyPokemonViewHolder, position: Int) {
        val pokemon = currentList[position]
        holder.bind(pokemon)
        holder.itemView.setOnClickListener {
            onClick(pokemon)
        }
    }


    private object FavoriteDiffCallback : DiffUtil.ItemCallback<PokemonWithDetailsModel>() {
        override fun areItemsTheSame(
            oldItem: PokemonWithDetailsModel,
            newItem: PokemonWithDetailsModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PokemonWithDetailsModel,
            newItem: PokemonWithDetailsModel
        ): Boolean {
            return oldItem == newItem
        }
    }

}