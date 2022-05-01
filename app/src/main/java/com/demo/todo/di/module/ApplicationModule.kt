package com.demo.todo.di.module

import android.content.Context
import androidx.room.Room
import com.demo.todo.data.TodoListDataBase
import com.demo.todo.data.dao.TodoListDao
import com.demo.todo.data.repository.TodoListRepository
import com.demo.todo.ui.createtodo.CreateTotoItemActivity
import com.demo.todo.utils.DATABASENAME
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideTodoListRepository(todoListDao: TodoListDao): TodoListRepository =
        TodoListRepository(
            todoListDao
        )

    @Singleton
    @Provides
    fun provideTodoDb(@ApplicationContext app: Context) = Room.databaseBuilder(
        app,
        TodoListDataBase::class.java,
        DATABASENAME
    ).build()

    @Singleton
    @Provides
    fun provideTodoListDao(db: TodoListDataBase) = db.getNoteDao()


    @Provides
    fun provideCreateTodoActivity() = CreateTotoItemActivity()

    @Provides
    @Singleton
    fun provideGson() = Gson()
}