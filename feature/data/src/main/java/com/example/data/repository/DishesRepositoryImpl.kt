package com.example.data.repository

import com.example.data.mapper.MapperDishToUiEntity
import com.example.data.remote.api.MainApi
import com.example.domain.entity.DishUiEntity
import com.example.domain.repository.DishesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DishesRepositoryImpl @Inject constructor(private val mainApi: MainApi) :
    DishesRepository {

    override fun fetchDishes(): Flow<List<DishUiEntity>> = flow {
        val result = mainApi.fetchDishes()
        emit(MapperDishToUiEntity().map(result.dishes))
    }
}