package com.murali.taskmanager.repository

import androidx.lifecycle.LiveData
import com.murali.taskmanager.data.local.CalenderTaskDao
import com.murali.taskmanager.data.local.CalenderTaskModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskRepository(val calenderTaskDao: CalenderTaskDao) {

    fun addTaskToDao(calenderTaskModel: CalenderTaskModel) {
        CoroutineScope(Dispatchers.IO).launch {
            calenderTaskDao.addTaskToRoomDataBase(calenderTaskModel)
        }
    }

    fun getAllTasksFromDao(): LiveData<List<CalenderTaskModel>> {
        return calenderTaskDao.getAllTasksFromRoomDataBase()
    }

    fun getAllTasksFromDaoAccordingToDate(date: String): LiveData<List<CalenderTaskModel>> {
        return calenderTaskDao.getAllTasksFromRoomDataBaseAccordingToDate(date)
    }

    fun deleteTaskInDao(calenderTaskModel: CalenderTaskModel) {
        CoroutineScope(Dispatchers.IO).launch {
            calenderTaskDao.deleteTaskFromRoomDataBase(calenderTaskModel)
        }
    }

}