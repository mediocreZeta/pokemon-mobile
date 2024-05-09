package com.zeta.pokemonapp.page.mypokemon

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeta.pokemonapp.R
import com.zeta.pokemonapp.databinding.FragmentMyPokemonBinding
import com.zeta.pokemonapp.page.pokemondetail.PokemonDetailsFragment
import com.zeta.pokemonapp.util.UiState
import com.zeta.pokemonapp.util.ViewBindingFragment
import com.zeta.pokemonapp.util.removeAllReference
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPokemonFragment : ViewBindingFragment<FragmentMyPokemonBinding>(
    FragmentMyPokemonBinding::inflate
) {

    private val myPokemonViewModel by viewModel<MyPokemonViewModel>()
    private var _adapter: MyPokemonAdapter? = null
    private val adapter get() = _adapter!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupAdapter()
        setupRecyclerView()
    }

    override fun onDestroyView() {
        binding.toolbarMyPokemon.removeAllReference()
        binding.homeRvMypokemon.removeAllReference()
        super.onDestroyView()
    }

    private fun setupAdapter() {
        _adapter = MyPokemonAdapter { pokemonModel ->
            val bundle = Bundle().apply {
                putString(PokemonDetailsFragment.ID, pokemonModel.id.toString())
                putString(PokemonDetailsFragment.NAME, pokemonModel.name)
            }
            findNavController().navigate(R.id.action_myPokemon_to_pokemonDetailsFragment, bundle)
        }
    }

    private fun setupToolbar() {
        binding.toolbarMyPokemon.apply {
            removeAllReference()
            setupWithNavController(findNavController())
            menu.clear()
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.homeRvMypokemon.layoutManager = layoutManager
        binding.homeRvMypokemon.adapter = adapter
        binding.homeRvMypokemon.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                layoutManager.orientation
            )
        )
        handleUiState()
    }

    private fun handleUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                myPokemonViewModel.myPokemonList.collect { state ->
                    binding.viewLoading.loading.isVisible = state is UiState.Loading
                    binding.viewError.tvErrorMessage.apply {
                        isVisible = state is UiState.Error || state is UiState.Empty
                        text = state.message
                    }
                    binding.homeRvMypokemon.isVisible = state is UiState.Success
                    if (state is UiState.Success) adapter.submitList(state.data)
                }
            }
        }
    }


}