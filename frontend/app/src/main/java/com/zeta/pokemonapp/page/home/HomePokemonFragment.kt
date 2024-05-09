package com.zeta.pokemonapp.page.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeta.pokemonapp.R
import com.zeta.pokemonapp.core.domain.model.PokemonModel
import com.zeta.pokemonapp.databinding.FragmentHomePokemonBinding
import com.zeta.pokemonapp.page.pokemondetail.PokemonDetailsFragment
import com.zeta.pokemonapp.util.ViewBindingFragment
import com.zeta.pokemonapp.util.removeAllReference
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomePokemonFragment : ViewBindingFragment<FragmentHomePokemonBinding>(
    FragmentHomePokemonBinding::inflate
) {
    private val homePokemonViewModel by viewModel<HomePokemonViewModel>()
    private var _pagingAdapter: PagingHomePokemonAdapter? = null
    private val pagingAdapter get() = _pagingAdapter!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecyclerView()
    }

    override fun onDestroyView() {
        binding.toolbarPokemon.removeAllReference()
        binding.homeRvPokemonList.removeAllReference()
        super.onDestroyView()
    }

    private fun setupRecyclerView() {
        _pagingAdapter = PagingHomePokemonAdapter {
            navigateToDetails(it)
        }
        val layoutManager = LinearLayoutManager(requireContext())
        binding.homeRvPokemonList.layoutManager = layoutManager
        binding.homeRvPokemonList.adapter = pagingAdapter.withLoadStateFooter(
            footer = PagingLoadStateAdapter(pagingAdapter::retry)
        )
        binding.homeRvPokemonList.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                layoutManager.orientation
            )
        )

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                pagingAdapter.loadStateFlow.collect { loadState ->
                    handleUiPager(loadState)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homePokemonViewModel.paginationFlow.collectLatest {
                    pagingAdapter.submitData(it)
                }
            }
        }

    }

    private fun setupToolbar() {
        binding.toolbarPokemon.apply {
            removeAllReference()
            setupWithNavController(findNavController())
            inflateMenu(R.menu.menu_home)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_myPokemon -> {
                        findNavController().navigate(R.id.action_pokemonFragment_to_myPokemon)
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun handleUiPager(loadState: CombinedLoadStates) {
        binding.viewLoading.loading.isVisible = loadState.refresh is LoadState.Loading
        binding.homeRvPokemonList.isVisible = loadState.refresh is LoadState.NotLoading
        binding.viewError.tvErrorMessage.apply {
            isVisible = loadState.refresh is LoadState.Error
            val refresh = loadState.refresh
            if (refresh is LoadState.Error) {
                text = refresh.error.toString()
            }
        }
        binding.viewError.btnRetry.apply {
            isVisible = loadState.refresh is LoadState.Error
            setOnClickListener { pagingAdapter.retry() }
        }

    }

    private fun navigateToDetails(pokemonModel: PokemonModel) {
        val bundle = Bundle().apply {
            putString(PokemonDetailsFragment.ID, pokemonModel.id)
            putString(PokemonDetailsFragment.NAME, pokemonModel.name)
        }
        findNavController().navigate(R.id.action_pokemonFragment_to_pokemonDetailsFragment, bundle)
    }

}

