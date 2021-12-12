package com.murali.taskmanager.dependency_injection

import android.content.Context
import androidx.room.Room
import com.murali.taskmanager.api.CalendarTasksAPI
import com.murali.taskmanager.data.local.CalendarTaskDao
import com.murali.taskmanager.data.local.CalendarTaskRoomDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CalendarTaskModule {

    @Singleton
    @Provides
    fun provideCalenderTaskAPIService(): CalendarTasksAPI {
        val builder = Retrofit.Builder()
            .baseUrl("http://13.232.92.136:8084/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return builder.create(CalendarTasksAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideCalenderTaskRoomDataBase(@ApplicationContext context: Context): CalendarTaskRoomDataBase {
        val builder = Room.databaseBuilder(
            context,
            CalendarTaskRoomDataBase::class.java,
            "calender_tasks"
        )
        builder.fallbackToDestructiveMigration()
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideCalenderTaskDAO(db: CalendarTaskRoomDataBase): CalendarTaskDao {
        return db.getCalenderTaskDao()
    }

}