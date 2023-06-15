package com.example.domain.repository

import com.example.domain.entity.CategoryUiEntity
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun fetchCategories(): Flow<List<CategoryUiEntity>>
}