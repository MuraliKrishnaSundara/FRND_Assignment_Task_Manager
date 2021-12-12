package com.murali.taskmanager.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.murali.taskmanager.data.response.get.ApiAndRoomDBCalendarTasksModel

@Database(entities = [ApiAndRoomDBCalendarTasksModel::class], version = 5)

abstract class CalendarTaskRoomDataBase : RoomDatabase() {

    abstract fun getCalenderTaskDao(): CalendarTaskDao

    companion object {

        private var calendarTaskInstance: CalendarTaskRoomDataBase? = null

        //creating room data base instance
        fun getRoomDataBaseObject(context: Context): CalendarTaskRoomDataBase {
            if (calendarTaskInstance == null) {
                val builder = Room.databaseBuilder(
                    context.applicationContext,
                    CalendarTaskRoomDataBase::class.java,
                    "calender_tasks"
                )
                builder.fallbackToDestructiveMigration()
                calendarTaskInstance = builder.build()
                return calendarTaskInstance!!
            } else
                return calendarTaskInstance!!
        }

    }

}