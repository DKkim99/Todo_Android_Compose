package com.dkproject.todoapp.data.DI

import android.app.Application
import android.content.Context
import com.dkproject.todoapp.data.Repository.CategoryRepositoryImpl
import com.dkproject.todoapp.data.Repository.TodoRepositoryImpl
import com.dkproject.todoapp.domain.repository.CategoryRepository
import com.dkproject.todoapp.domain.repository.TodoRepository
import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindContext(application: Application): Context
    @Binds
    abstract fun bindTodoRepository(todoRepositoryImpl: TodoRepositoryImpl): TodoRepository

    @Binds
    abstract fun bindCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl) : CategoryRepository
}