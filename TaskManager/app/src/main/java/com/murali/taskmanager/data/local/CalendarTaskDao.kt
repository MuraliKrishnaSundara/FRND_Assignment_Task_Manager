package com.murali.taskmanager.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.murali.taskmanager.data.response.get.CalendarTaskModel

@Dao
interface CalendarTaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTasksListFromApiToRoomDataBase(calendarTaskModel: ArrayList<CalendarTaskModel>)

    @Query("select * from calender_tasks_table")
    fun getAllTasksFromRoomDataBase(): LiveData<List<CalendarTaskModel>>

    @Query("select * from calender_tasks_table where date = :date")
    fun getAllTasksFromRoomDataBaseAccordingToDate(date: String): LiveData<List<CalendarTaskModel>>

    @Query("delete from calender_tasks_table")
    fun deleteAllTasksFromRoomDataBase()

}