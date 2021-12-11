package com.murali.taskmanager.data.local

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaskModel::class], version = 2)
abstract class RoomDataBaseClass : RoomDatabase() {
    abstract fun getDao(): ClassDao

    companion object {
        private var INSTANCE: RoomDataBaseClass? = null

        fun getDataBaseObject(context: Context): RoomDataBaseClass {
            if (INSTANCE == null) {
                val builder = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDataBaseClass::class.java,
                    "db_name"
                )
                builder.fallbackToDestructiveMigration()
                INSTANCE = builder.build()
                return INSTANCE!!
            } else return INSTANCE!!
        }

    }

}