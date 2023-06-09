package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.db.AppDatabase
import com.example.data.local.db.BasketItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "FoodOrder.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBasketItemDao(appDatabase: AppDatabase): BasketItemDao {
        return appDatabase.basketItemDao()
    }
}