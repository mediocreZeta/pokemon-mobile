package com.zeta.pokemonapp.page.pokemondetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeta.pokemonapp.core.domain.model.PokemonWithDetailsModel
import com.zeta.pokemonapp.core.domain.usecase.AddToMyPokemonUseCase
import com.zeta.pokemonapp.core.domain.usecase.CatchPokemonUseCase
import com.zeta.pokemonapp.core.domain.usecase.ReleasePokemonUseCase
import com.zeta.pokemonapp.core.domain.usecase.RenamePokemonUseCase
import com.zeta.pokemonapp.core.domain.usecase.SearchPokemonUseCase
import com.zeta.pokemonapp.util.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(
    searchPokemonUseCase: SearchPokemonUseCase,
    private val releasePokemonUseCase: ReleasePokemonUseCase,
    private val addToMyPokemonUseCase: AddToMyPokemonUseCase,
    private val catchPokemonUseCase: CatchPokemonUseCase,
    private val renamePokemonUseCase: RenamePokemonUseCase,
    private val id: String,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<PokemonWithDetailsModel>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _notification = MutableSharedFlow<NotificationState>()
    val notification = _notification.asSharedFlow()

    init {
        viewModelScope.launch {
            val response = searchPokemonUseCase(id)
            handleResponse(response)
        }
    }

    private fun handleResponse(response: PokemonWithDetailsModel?) {
        try {
            if (response == null) {
                _uiState.update { UiState.Empty }
            } else {
                _uiState.update { UiState.Success(response) }
            }
        } catch (e: Exception) {
            _uiState.update { UiState.Error(e.localizedMessage ?: "Error") }
        }
    }

    fun renamePokemon(pokemonWithDetailsModel: PokemonWithDetailsModel) {
        viewModelScope.launch {
            val isSuccess = renamePokemonUseCase(pokemonWithDetailsModel)
            val notificationStatus = NotificationState(
                NotificationType.RENAME,
                isSuccess,
                pokemonWithDetailsModel
            )
            _notification.emit(notificationStatus)
        }
    }

    fun releasePokemon(pokemonWithDetailsModel: PokemonWithDetailsModel) {
        viewModelScope.launch {
            val isSuccess = releasePokemonUseCase(pokemonWithDetailsModel)
            val notificationStatus = NotificationState(
                NotificationType.RELEASE,
                isSuccess,
                pokemonWithDetailsModel
            )
            _notification.emit(notificationStatus)
        }
    }

    fun capturePokemon(pokemonWithDetailsModel: PokemonWithDetailsModel) {
        viewModelScope.launch {
            val isSuccess = catchPokemonUseCase()
            val notificationStatus = NotificationState(
                NotificationType.CATCH,
                isSuccess,
                pokemonWithDetailsModel
            )
            _notification.emit(notificationStatus)
        }
    }

    fun addToMyPokemon(pokemonWithDetailsModel: PokemonWithDetailsModel, name: String) {
        viewModelScope.launch {
            val newPokemon = pokemonWithDetailsModel.copy(name = name)
            val isSuccess = addToMyPokemonUseCase(newPokemon)
            val notificationStatus = NotificationState(
                NotificationType.ADD,
                isSuccess,
                pokemonWithDetailsModel
            )
            _notification.emit(notificationStatus)
        }

    }
}

data class NotificationState(
    val notificationType: NotificationType,
    val isSuccess: Boolean,
    val pokemonWithDetailsModel: PokemonWithDetailsModel
)


enum class NotificationType(val title: String) {
    CATCH("Catch"),
    RELEASE("Release"),
    RENAME("Rename"),
    ADD("Add")
}



