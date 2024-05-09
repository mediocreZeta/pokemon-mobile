package com.zeta.pokemonapp.page.pokemondetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.zeta.pokemonapp.R
import com.zeta.pokemonapp.core.domain.model.PokemonWithDetailsModel
import com.zeta.pokemonapp.databinding.FragmentPokemonDetailsBinding
import com.zeta.pokemonapp.util.CatchPokemonDialog
import com.zeta.pokemonapp.util.RenameReleasePokemonDialog
import com.zeta.pokemonapp.util.UiState
import com.zeta.pokemonapp.util.ViewBindingFragment
import com.zeta.pokemonapp.util.removeAllReference
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class PokemonDetailsFragment : ViewBindingFragment<FragmentPokemonDetailsBinding>(
    FragmentPokemonDetailsBinding::inflate
) {
    private val pokemonId: String
        get() = requireArguments().getString(ID)!!
    private val pokemonName: String
        get() = requireArguments().getString(NAME)!!

    private var _adapter: PokemonMovesAdapter? = null
    private val adapter get() = _adapter!!

    private val pokemonDetailsViewModel by viewModel<PokemonDetailsViewModel> {
        parametersOf(pokemonId)
    }

    private lateinit var renameReleasePokemonDialog: RenameReleasePokemonDialog
    private lateinit var catchPokemonDialog: CatchPokemonDialog
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renameReleasePokemonDialog = RenameReleasePokemonDialog(requireActivity())
        catchPokemonDialog = CatchPokemonDialog(requireActivity())
        _adapter = PokemonMovesAdapter()
        setupToolbar()
        setupNotification()
        handleUiState()
    }

    override fun onDestroyView() {
        binding.toolbarPokemonDetails.removeAllReference()
        binding.rvPokemonDetailsSkillDetails.removeAllReference()
        super.onDestroyView()
    }

    private fun setupToolbar() {
        binding.toolbarPokemonDetails.apply {
            removeAllReference()
            setupWithNavController(findNavController())
        }
    }

    private fun handleUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                pokemonDetailsViewModel.uiState.collect { state ->
                    binding.viewLoading.loading.isVisible = state is UiState.Loading
                    binding.viewError.tvErrorMessage.apply {
                        isVisible = state is UiState.Error || state is UiState.Empty
                        text = state.message
                    }
                    binding.btnPokemonDetailsCatch.isVisible = state is UiState.Success
                    if (state is UiState.Success) handleAction(state.data)
                    handlePokemonDetails(state.output, state is UiState.Success)
                }
            }
        }
    }

    private fun handleAction(pokemonDetails: PokemonWithDetailsModel) {
        val previousFragment = findNavController().previousBackStackEntry?.destination?.id
        previousFragment?.let {
            when (previousFragment) {
                R.id.homePokemonFragment -> {
                    handleCatch(pokemonDetails)
                }

                R.id.myPokemon -> {
                    handleRelease(pokemonDetails)
                    handleRename(pokemonDetails)
                }

                else -> {}
            }
        }
    }

    private fun setupNotification() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                pokemonDetailsViewModel.notification.collect {
                    handleNotification(it)
                }

            }
        }
    }

    private fun handleNotification(state: NotificationState) {
        when (state.isSuccess) {
            false -> handleFailedProcess(state)
            true -> handleSuccessProcess(state)
        }
    }

    private fun handleSuccessProcess(state: NotificationState) {
        val notificationType = state.notificationType
        val pokemonDetails = state.pokemonWithDetailsModel
        val onPositiveClick = { parentFragmentManager.popBackStack() }
        val onNegativeClick = { parentFragmentManager.popBackStack() }

        when (notificationType) {
            NotificationType.CATCH -> {
                val onSubmitClick: (String) -> Unit = {
                    pokemonDetailsViewModel.addToMyPokemon(
                        pokemonDetails,
                        it
                    )

                }
                catchPokemonDialog.createDialog(
                    onSubmitClick,
                    onNegativeClick,
                    state.pokemonWithDetailsModel.name
                )
            }

            else -> {
                renameReleasePokemonDialog.createDialog(
                    onPositiveClick,
                    onNegativeClick,
                    state.notificationType.title,
                    state.isSuccess
                )
            }
        }
    }

    private fun handleFailedProcess(state: NotificationState) {
        val notificationType = state.notificationType
        val pokemonDetails = state.pokemonWithDetailsModel
        var onPositiveClick = { parentFragmentManager.popBackStack() }
        val onNegativeClick = { parentFragmentManager.popBackStack() }

        when (notificationType) {
            NotificationType.RELEASE -> {
                onPositiveClick = { pokemonDetailsViewModel.releasePokemon(pokemonDetails) }
            }

            NotificationType.RENAME -> {
                onPositiveClick = { pokemonDetailsViewModel.renamePokemon(pokemonDetails) }

            }

            NotificationType.CATCH -> {
                onPositiveClick = { pokemonDetailsViewModel.capturePokemon(pokemonDetails) }
            }

            NotificationType.ADD -> {
                Toast.makeText(requireContext(), "Failed to add pokemon", Toast.LENGTH_SHORT).show()
            }
        }

        renameReleasePokemonDialog.createDialog(
            onPositiveClick,
            onNegativeClick,
            state.notificationType.title,
            state.isSuccess
        )

    }

    private fun handleRelease(pokemonDetails: PokemonWithDetailsModel) {
        binding.btnPokemonDetailsCatch.apply {
            isEnabled = true
            text = resources.getString(R.string.action_release)
            setOnClickListener {
                pokemonDetailsViewModel.releasePokemon(pokemonDetails)
                isEnabled = false
            }
        }
    }

    private fun handleCatch(pokemonDetails: PokemonWithDetailsModel) {
        binding.btnPokemonDetailsCatch.apply {
            isEnabled = true
            val isExistInDatabase =
                pokemonName == pokemonDetails.name && pokemonId == pokemonDetails.id.toString()
            if (isExistInDatabase) {
                text = resources.getString(R.string.action_catch)
            } else {
                text = context.getString(R.string.descriptino_exist_in_db)
                isEnabled = false
            }
            setOnClickListener {
                pokemonDetailsViewModel.capturePokemon(pokemonDetails)
                isEnabled = false
            }
        }
    }

    private fun handleRename(pokemonDetails: PokemonWithDetailsModel) {
        binding.btnPokemonDetailsRename.apply {
            isEnabled  = true
            text = resources.getString(R.string.action_rename)
            isVisible = true
            setOnClickListener {
                isEnabled = false
                pokemonDetailsViewModel.renamePokemon(pokemonDetails)
            }
        }
    }

    private fun handlePokemonDetails(pokemonDetails: PokemonWithDetailsModel?, isVisible: Boolean) {
        binding.tvPokemonDetailsSkillTitle.isVisible = isVisible
        binding.rvPokemonDetailsSkillDetails.isVisible = isVisible
        binding.viewDivider.isVisible = isVisible
        if (pokemonDetails == null) return
        with(pokemonDetails) {
            binding.tvPokemonDetailsName.text = name
            binding.tvPokemonDetailsTypeTitle.text = resources.getString(
                R.string.description_type,
                types.toString()
            )
            Glide.with(requireContext())
                .load(image)
                .error(AppCompatResources.getDrawable(requireContext(), R.drawable.ic_broken_image))
                .into(binding.imgPokemonDetails)
        }
        setupRecyclerView(pokemonDetails.moveNames)
    }

    private fun setupRecyclerView(moves: List<String>) {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvPokemonDetailsSkillDetails.layoutManager = layoutManager
        binding.rvPokemonDetailsSkillDetails.adapter = adapter
        adapter.submitList(moves)
    }


    companion object {
        const val ID = "PokemonDetailsId"
        const val NAME = "PokemonDetailsName"
    }
}