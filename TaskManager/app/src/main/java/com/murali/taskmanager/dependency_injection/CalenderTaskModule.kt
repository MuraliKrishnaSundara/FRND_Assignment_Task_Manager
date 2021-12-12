package com.murali.taskmanager.dependency_injection

import android.content.Context
import androidx.room.Room
import com.murali.taskmanager.data.api.CalenderTasksAPI
import com.murali.taskmanager.data.local.CalenderTaskDao
import com.murali.taskmanager.data.local.CalenderTaskRoomDataBase
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
object CalenderTaskModule {

    @Singleton
    @Provides
    fun provideCalenderTaskAPIService(): CalenderTasksAPI {
        val builder = Retrofit.Builder()
            .baseUrl("http://13.232.92.136:8084/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return builder.create(CalenderTasksAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideCalenderTaskRoomDataBase(@ApplicationContext context: Context): CalenderTaskRoomDataBase {
        val builder = Room.databaseBuilder(
            context,
            CalenderTaskRoomDataBase::class.java,
            "calender_tasks"
        )
        builder.fallbackToDestructiveMigration()
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideCalenderTaskDAO(db: CalenderTaskRoomDataBase): CalenderTaskDao {
        return db.getCalenderTaskDao()
    }

}