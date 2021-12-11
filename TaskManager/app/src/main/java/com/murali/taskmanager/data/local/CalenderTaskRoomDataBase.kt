package com.murali.taskmanager.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CalenderTaskModel::class], version = 4)

abstract class CalenderTaskRoomDataBase : RoomDatabase() {

    abstract fun getCalenderTaskDao(): CalenderTaskDao

    companion object {
        private var CalenderTaskInstance: CalenderTaskRoomDataBase? = null

        fun getRoomDataBaseObject(context: Context): CalenderTaskRoomDataBase {
            if (CalenderTaskInstance == null) {
                val builder = Room.databaseBuilder(
                    context.applicationContext,
                    CalenderTaskRoomDataBase::class.java,
                    "calender_tasks"
                )
                builder.fallbackToDestructiveMigration()
                CalenderTaskInstance = builder.build()
                return CalenderTaskInstance!!
            } else
                return CalenderTaskInstance!!
        }

    }

}