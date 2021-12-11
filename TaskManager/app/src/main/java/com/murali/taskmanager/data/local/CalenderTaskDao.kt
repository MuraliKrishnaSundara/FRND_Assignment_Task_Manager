package com.murali.taskmanager.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CalenderTaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTaskToRoomDataBase(calenderTaskModel: CalenderTaskModel)

    @Query("select * from calender_tasks_table")
    fun getAllTasksFromRoomDataBase(): LiveData<List<CalenderTaskModel>>

    @Query("select * from calender_tasks_table where date = :date")
    fun getAllTasksFromRoomDataBaseAccordingToDate(date: String): LiveData<List<CalenderTaskModel>>

    @Delete
    fun deleteTaskFromRoomDataBase(calenderTaskModel: CalenderTaskModel)

}