package com.example.foodorderapp.home

import androidx.lifecycle.viewModelScope
import com.example.domain.delegate.CurrentAddressDelegate
import com.example.domain.entity.CategoryUiEntity
import com.example.domain.usecase.GetCategoryUseCase
import com.example.foodorderapp.R
import com.example.foodorderapp.core.base.BaseViewModel
import com.example.foodorderapp.core.navigation.Command
import com.example.foodorderapp.util.TextSource
import com.example.foodorderapp.util.event.InfoEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCategoryUseCase: GetCategoryUseCase,
    val currentAddressDelegate: CurrentAddressDelegate,
) : BaseViewModel() {

    private val _categoriesList = MutableStateFlow<List<CategoryUiEntity>?>(null)
    val categoriesList = _categoriesList.asStateFlow()

    init {
        getCategories()
    }

    private fun getCategories() {
        getCategoryUseCase()
            .onEach {
                _categoriesList.value = it
            }
            .catch {
                emitInfoEvent(
                    InfoEvent.Info(
                        title = TextSource.Resource(R.string.something_went_wrong),
                        message = TextSource.Dynamic(it.message ?: ""),
                        buttonText = TextSource.Resource(R.string.ok)
                    )
                )
            }
            .launchIn(viewModelScope)
    }

    fun navigateToDishFragment(title: String) {
        val dir = HomeFragmentDirections.toDishesFragment(title)
        sendCommand(Command.NavCommand(dir))
    }

}