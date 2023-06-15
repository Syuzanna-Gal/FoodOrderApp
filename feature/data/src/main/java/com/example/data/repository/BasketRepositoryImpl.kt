package com.example.data.repository

import com.example.data.local.db.BasketItemDao
import com.example.data.mapper.MapperBasketItemToDbEntity
import com.example.data.mapper.MapperBasketItemToUiEntity
import com.example.data.mapper.MapperDishUiEntityToBasketItemDbEntity
import com.example.domain.entity.BasketItemUiEntity
import com.example.domain.entity.DishUiEntity
import com.example.domain.repository.BasketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BasketRepositoryImpl @Inject constructor(private val basketItemDao: BasketItemDao) :
    BasketRepository {

    override fun subscribeBasketItems(): Flow<List<BasketItemUiEntity>> =
        basketItemDao.findAllAsFlow().map {
            MapperBasketItemToUiEntity().map(it)
        }


    override suspend fun addToBasket(dishUiEntity: DishUiEntity, quantity: Int) {
        basketItemDao.insert(MapperDishUiEntityToBasketItemDbEntity(quantity).map(dishUiEntity))
    }

    override suspend fun deleteFromBasket(basketItemUiEntity: BasketItemUiEntity) {
        MapperBasketItemToDbEntity().map(basketItemUiEntity).let {
            basketItemDao.delete(it)
        }
    }

    override suspend fun updateBasketItem(basketItemUiEntity: BasketItemUiEntity) {
        MapperBasketItemToDbEntity().map(basketItemUiEntity).let {
            basketItemDao.update(it)
        }
    }

    override fun getBasketItemById(id: Int): BasketItemUiEntity? =
        basketItemDao.findById(id)?.let { MapperBasketItemToUiEntity().map(it) }


    override suspend fun deleteAll() = basketItemDao.deleteAll()
}