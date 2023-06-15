package com.example.foodorderapp.basket

import androidx.lifecycle.viewModelScope
import com.example.domain.delegate.CurrentAddressDelegate
import com.example.domain.entity.BasketItemUiEntity
import com.example.domain.usecase.RemoveAllBasketItemsUseCase
import com.example.domain.usecase.RemoveFromBasketUseCase
import com.example.domain.usecase.SubscribeBasketItemsUseCase
import com.example.domain.usecase.UpdateBasketItemUseCase
import com.example.foodorderapp.R
import com.example.foodorderapp.core.base.BaseViewModel
import com.example.foodorderapp.util.TextSource
import com.example.foodorderapp.util.event.InfoEvent
import com.example.foodorderapp.util.type_alias.RString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val subscribeBasketItemsUseCase: SubscribeBasketItemsUseCase,
    private val updateBasketItemUseCase: UpdateBasketItemUseCase,
    private val removeFromBasketUseCase: RemoveFromBasketUseCase,
    private val removeAllBasketItemsUseCase: RemoveAllBasketItemsUseCase,
    val currentAddressDelegate: CurrentAddressDelegate,
) : BaseViewModel() {

    private val _basketItemsList = MutableStateFlow<List<BasketItemUiEntity>?>(null)
    val basketItemsList = _basketItemsList.asStateFlow()

    private val _paymentAmount = MutableStateFlow<Double?>(null)
    val paymentAmount = _paymentAmount.asStateFlow()

    init {
        getBasketItems()
    }

    fun getBasketItems() {
        subscribeBasketItemsUseCase.invoke()
            .onEach { basketItems ->
                _basketItemsList.value = basketItems
                getPaymentAmount(basketItems)
            }
            .catch {
                emitInfoEvent(
                    InfoEvent.Info(
                        title = TextSource.Resource(R.string.something_went_wrong),
                        message = TextSource.Dynamic(it.message ?: ""),
                        buttonText = TextSource.Resource(R.string.ok)
                    )
                )
            }.launchIn(viewModelScope)
    }

    fun addItem(basketItemUiEntity: BasketItemUiEntity) {
        viewModelScope.launch {
            try {
                updateBasketItemUseCase(basketItemUiEntity)
            } catch (e: java.lang.Exception) {
                emitInfoEvent(
                    InfoEvent.Info(
                        title = TextSource.Resource(R.string.something_went_wrong),
                        message = TextSource.Resource(RString.update_item_error),
                        buttonText = TextSource.Resource(R.string.ok)
                    )
                )
            }
        }
    }

    fun removeItem(basketItemUiEntity: BasketItemUiEntity) {
        viewModelScope.launch {
            try {
                removeFromBasketUseCase(basketItemUiEntity)
            } catch (e: java.lang.Exception) {
                emitInfoEvent(
                    InfoEvent.Info(
                        title = TextSource.Resource(R.string.something_went_wrong),
                        message = TextSource.Resource(RString.remove_item_error),
                        buttonText = TextSource.Resource(R.string.ok)
                    )
                )
            }
        }
    }

    fun removeAll() {
        viewModelScope.launch {
            try {
                removeAllBasketItemsUseCase.invoke()
            } catch (e: java.lang.Exception) {
                emitInfoEvent(
                    InfoEvent.Info(
                        title = TextSource.Resource(R.string.something_went_wrong),
                        message = TextSource.Resource(com.example.coreui.R.string.remove_all_items_error),
                        buttonText = TextSource.Resource(R.string.ok)
                    )
                )
            }
        }
    }

    private fun getPaymentAmount(list: List<BasketItemUiEntity?>) {
        var price = 0.0
        list.forEach {
            price += ((it?.price ?: 0.0) * (it?.quantity ?: 0))
        }
        _paymentAmount.value = price
    }
}