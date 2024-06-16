package com.dkproject.todoapp.data.DI

import android.content.Context
import androidx.room.Room
import com.dkproject.todo.domain.usecase.GetTodoByIdUseCase
import com.dkproject.todo.domain.usecase.UpdateTodoCategoryAndColorUseCase
import com.dkproject.todoapp.data.local.LocalTodoDataSource
import com.dkproject.todoapp.data.local.TodoDao
import com.dkproject.todoapp.data.local.TodoDatabase
import com.dkproject.todoapp.domain.repository.CategoryRepository
import com.dkproject.todoapp.domain.repository.TodoRepository
import com.dkproject.todoapp.domain.usecase.GetCategoryUseCase
import com.dkproject.todoapp.domain.usecase.GetTodoByDateUseCase
import com.dkproject.todoapp.domain.usecase.InsertTodoUseCase
import com.dkproject.todoapp.domain.usecase.SetCategoryUseCase
import com.dkproject.todoapp.domain.usecase.UpdateTodoUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideGson():Gson{
        return Gson()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context:Context):TodoDatabase{
        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "todo_db"
        ).build()
    }

    @Provides
    fun provideTodoDao(db:TodoDatabase) :TodoDao = db.todoDao()


    @Provides
    fun provideLocalTodoDataSource(todoDao: TodoDao): LocalTodoDataSource {
        return LocalTodoDataSource(todoDao)
    }

    @Provides
    fun provideGetTodoByDate(todoRepository: TodoRepository):GetTodoByDateUseCase{
        return GetTodoByDateUseCase(todoRepository)
    }

    @Provides
    fun provideInsertTodoUseCase(todoRepository: TodoRepository):InsertTodoUseCase{
        return InsertTodoUseCase(todoRepository)
    }

    @Provides
    fun provideUpdateTodoUseCase(todoRepository: TodoRepository):UpdateTodoUseCase{
        return UpdateTodoUseCase(todoRepository)
    }

    @Provides
    fun provideGetCategoryUseCase(categoryRepository:CategoryRepository):GetCategoryUseCase{
        return GetCategoryUseCase(categoryRepository = categoryRepository)
    }

    @Provides
    fun provideSetCategoryUseCase(categoryRepository: CategoryRepository) : SetCategoryUseCase{
        return SetCategoryUseCase(categoryRepository = categoryRepository)
    }

    @Provides
    fun provideGetTodoByIdUseCase(todoRepository: TodoRepository) : GetTodoByIdUseCase {
        return GetTodoByIdUseCase(todoRepository = todoRepository)
    }

    @Provides
    fun provideUpdateTodoCategoryAndColor(todoRepository: TodoRepository) : UpdateTodoCategoryAndColorUseCase{
        return UpdateTodoCategoryAndColorUseCase(todoRepository = todoRepository )
    }
}