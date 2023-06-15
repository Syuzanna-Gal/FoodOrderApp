package com.example.domain.usecase

import com.example.domain.entity.CategoryUiEntity
import com.example.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(private val repository: CategoryRepository) {
    operator fun invoke(): Flow<List<CategoryUiEntity>> =
        repository.fetchCategories().flowOn(Dispatchers.IO)
}