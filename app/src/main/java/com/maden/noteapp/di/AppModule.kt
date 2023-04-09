package com.maden.noteapp.di

import android.app.Application
import androidx.room.Room
import com.maden.noteapp.data.local.AppDatabase
import com.maden.noteapp.data.repository.NoteRepositoryImpl
import com.maden.noteapp.domain.repository.NoteRepository
import com.maden.noteapp.domain.use_case.NoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application) : AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java, AppDatabase.DATABASE_NAME
        ).build()
    }


    @Provides
    @Singleton
    fun provideNoteRepository(db: AppDatabase) : NoteRepository {
        return NoteRepositoryImpl(db.userDao())
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository) : NoteUseCase {
        return NoteUseCase(repository)
    }
}
