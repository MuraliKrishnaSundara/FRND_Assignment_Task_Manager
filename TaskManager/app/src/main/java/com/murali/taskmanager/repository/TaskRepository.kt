package com.murali.taskmanager.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.murali.taskmanager.data.api.Network
import com.murali.taskmanager.data.api.ResponseHandler
import com.murali.taskmanager.data.api.TasksAPI
import com.murali.taskmanager.data.local.CalenderTaskDao
import com.murali.taskmanager.data.local.CalenderTaskModel
import com.murali.taskmanager.data.response.get.GetTasksRequestDTO
import com.murali.taskmanager.data.response.get.GetTasksResponseDTO
import com.murali.taskmanager.data.response.post.PostTasksRequestDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskRepository(val calenderTaskDao: CalenderTaskDao) {

    private val api: TasksAPI = Network.getRetrofit().create(TasksAPI::class.java)

    //adding task to api
    fun addTaskApiToDao(calenderTaskModel: PostTasksRequestDTO) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = api.addTaskToAPI(calenderTaskModel)
            if (response.status == "Success") {
                Log.d("murali", "successfully task added")
            } else {
                Log.d("murali", "error task not added ${response.status}")
            }
        }
    }

    //getting tasks from api and adding to room database
    fun getAllTasksFromApiAndAddToRoomDataBase(getTasksRequestDTO: GetTasksRequestDTO) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = api.getTasksFromAPI(getTasksRequestDTO)
            if (!response.tasks.isEmpty()) {
                storeInCalenderTaskRoomDataBase(response)
                Log.d("murali", "Response getting success")
            }
            Log.d("murali", "Response getting error")
        }
    }

    //adding tasks to room database which are coming from api
    private fun storeInCalenderTaskRoomDataBase(response: GetTasksResponseDTO) {
        val listOfTasks = ArrayList<CalenderTaskModel>()
        response.tasks.forEach {
            val newTask = CalenderTaskModel(
                it.task_id,
                it.task_detail.name,
                it.task_detail.description,
                it.task_detail.date
            )
            listOfTasks.add(newTask)
        }
        //deleting all tasks in room database
        calenderTaskDao.deleteAllTasksFromRoomDataBase()
        //after deleting all tasks in room database adding fresh tasks list from api
        calenderTaskDao.addTasksListFromApiToRoomDataBase(listOfTasks)
    }

    //getting all tasks from room database using livedata
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