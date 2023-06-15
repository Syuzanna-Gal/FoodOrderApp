package com.example.domain.repository

import com.example.domain.entity.BasketItemUiEntity
import com.example.domain.entity.DishUiEntity
import kotlinx.coroutines.flow.Flow

interface BasketRepository {

    fun subscribeBasketItems(): Flow<List<BasketItemUiEntity>>

    suspend fun addToBasket(dishUiEntity: DishUiEntity, quantity: Int)

    suspend fun deleteFromBasket(basketItemUiEntity: BasketItemUiEntity)

    suspend fun updateBasketItem(basketItemUiEntity: BasketItemUiEntity)

    fun getBasketItemById(id: Int): BasketItemUiEntity?

    suspend fun deleteAll()

}
