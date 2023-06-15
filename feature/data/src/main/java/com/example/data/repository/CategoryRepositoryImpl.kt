package com.example.data.repository

import com.example.data.mapper.MapperCategoryToUiEntity
import com.example.data.remote.api.MainApi
import com.example.domain.entity.CategoryUiEntity
import com.example.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(private val mainApi: MainApi) :
    CategoryRepository {
    override fun fetchCategories(): Flow<List<CategoryUiEntity>> = flow {
        val result = mainApi.fetchCategories()
        emit(MapperCategoryToUiEntity().map(result.categories))
    }
}