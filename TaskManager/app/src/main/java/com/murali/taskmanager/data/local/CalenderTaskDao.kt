package com.murali.taskmanager.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.murali.taskmanager.data.response.get.CalenderTaskModel

@Dao
interface CalenderTaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTasksListFromApiToRoomDataBase(calenderTaskModel: ArrayList<CalenderTaskModel>)

    @Query("select * from calender_tasks_table")
    fun getAllTasksFromRoomDataBase(): LiveData<List<CalenderTaskModel>>

    @Query("select * from calender_tasks_table where date = :date")
    fun getAllTasksFromRoomDataBaseAccordingToDate(date: String): LiveData<List<CalenderTaskModel>>

    @Query("delete from calender_tasks_table")
    fun deleteAllTasksFromRoomDataBase()

}