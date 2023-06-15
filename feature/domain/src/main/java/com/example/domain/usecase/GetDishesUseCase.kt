package com.example.domain.usecase

import com.example.domain.entity.DishUiEntity
import com.example.domain.repository.DishesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetDishesUseCase @Inject constructor(private val repository: DishesRepository) {
    operator fun invoke(): Flow<List<DishUiEntity>> =
        repository.fetchDishes().flowOn(Dispatchers.IO)
}