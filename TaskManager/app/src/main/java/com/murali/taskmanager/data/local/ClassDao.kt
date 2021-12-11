package com.murali.taskmanager.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ClassDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDataInTask(taskModel: TaskModel)

    @Update
    fun updateDataInTask(taskModel: TaskModel)

    @Query("select * from task_table")
    fun fetchDataFromTask(): LiveData<List<TaskModel>>

    @Query("select * from task_table where date = :date")
    fun getTaskByDate(date: String): LiveData<List<TaskModel>>

}