package com.murali.taskmanager.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.murali.taskmanager.data.response.get.ApiAndRoomDBCalendarTasksModel

@Dao
interface CalendarTaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTasksListFromApiToRoomDataBase(apiAndRoomDBCalendarTasksModel: ArrayList<ApiAndRoomDBCalendarTasksModel>)

    @Query("select * from calender_tasks_table")
    fun getAllTasksFromRoomDataBase(): LiveData<List<ApiAndRoomDBCalendarTasksModel>>

    @Query("select * from calender_tasks_table where date = :date")
    fun getAllTasksFromRoomDataBaseAccordingToDate(date: String): LiveData<List<ApiAndRoomDBCalendarTasksModel>>

    @Query("delete from calender_tasks_table")
    fun deleteAllTasksFromRoomDataBase()

}