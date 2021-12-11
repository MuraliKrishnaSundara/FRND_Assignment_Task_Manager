package com.murali.taskmanager.repository

import androidx.lifecycle.LiveData
import com.murali.taskmanager.data.api.Network
import com.murali.taskmanager.data.api.ResponseHandler
import com.murali.taskmanager.data.api.TasksAPI
import com.murali.taskmanager.data.local.CalenderTaskDao
import com.murali.taskmanager.data.local.CalenderTaskModel
import com.murali.taskmanager.data.response.GetTasksResponseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TaskRepository @Inject constructor(val calenderTaskDao: CalenderTaskDao) {

    private val api: TasksAPI = Network.getRetrofit().create(TasksAPI::class.java)
    private val responseHandler = ResponseHandler()
    private val user_id = 1005

    fun getRemoteTasks() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = api.getTasksFromAPI(user_id)
            storeInCalenderTaskRoomDataBase(response)
        }
    }

    private fun storeInCalenderTaskRoomDataBase(response: GetTasksResponseModel) {
        val listOfTasks = ArrayList<CalenderTaskModel>()
        response.forEach {
            val newTask = CalenderTaskModel(it.name, it.description, it.date)
            listOfTasks.add(newTask)
        }
        calenderTaskDao.deleteAllTasksFromRoomDataBase()
        calenderTaskDao.addTasksListFromApiToRoomDataBase(listOfTasks)
    }

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