package com.example.foodorderapp.home.dishes

import androidx.lifecycle.viewModelScope
import com.example.domain.entity.DishUiEntity
import com.example.domain.usecase.GetDishesUseCase
import com.example.foodorderapp.R
import com.example.foodorderapp.core.base.BaseViewModel
import com.example.foodorderapp.core.navigation.Command
import com.example.foodorderapp.util.TextSource
import com.example.foodorderapp.util.event.InfoEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DishesViewModel @Inject constructor(
    private val getDishesUseCase: GetDishesUseCase,
) : BaseViewModel() {

    private val _dishesList = MutableStateFlow<List<DishUiEntity>?>(null)
    val dishesList = _dishesList.asStateFlow()

    private val _tagsList = MutableStateFlow<Set<String>?>(null)
    val tagsList = _tagsList.asStateFlow()

    private var _allDishes = listOf<DishUiEntity>()

    init {
        getDishesList()
    }

    private fun getDishesList() {
        getDishesUseCase.invoke()
            .onEach { dishes ->
                _tagsList.value =
                    dishes.flatMap { it.tags }.toSet()
                _allDishes = dishes
                _dishesList.value = dishes
            }
            .catch {
                emitInfoEvent(
                    InfoEvent.Info(
                        title = TextSource.Resource(R.string.something_went_wrong),
                        message = TextSource.Resource(R.string.something_went_wrong),
                        buttonText = TextSource.Resource(R.string.ok)
                    )
                )
            }.launchIn(viewModelScope)
    }

    fun updateDishesAccordingTag(tagTitle: String) {
        _dishesList.update {
            _allDishes.filter { it.tags.contains(tagTitle) }
        }
    }

    fun navigateToDishDetails(dish: DishUiEntity) {
        val dir = DishesFragmentDirections.toDishDetailsDialogFragment(dish = dish)
        sendCommand(Command.NavCommand(dir))
    }

}