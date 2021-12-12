package com.murali.taskmanager.repository

import android.util.Log
import androidx.lifecycle.LiveData

import com.murali.taskmanager.api.Network
import com.murali.taskmanager.api.CalenderTasksAPI
import com.murali.taskmanager.data.local.CalenderTaskDao
import com.murali.taskmanager.data.response.get.CalenderTaskModel
import com.murali.taskmanager.data.response.delete.DeleteTaskRequestDTO
import com.murali.taskmanager.data.response.get.GetTasksRequestDTO
import com.murali.taskmanager.data.response.get.GetTasksResponseDTO
import com.murali.taskmanager.data.response.post.PostTasksRequestDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskRepository(val calenderTaskDao: CalenderTaskDao) {

    private val apiCalender: CalenderTasksAPI =
        Network.retrofit.create(CalenderTasksAPI::class.java)

    //adding task to api
    fun addTaskApiToDao(calenderTaskModel: PostTasksRequestDTO) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiCalender.addTaskToAPI(calenderTaskModel)
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
            val response = apiCalender.getTasksFromAPI(getTasksRequestDTO)
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

    fun deleteTaskInApi(
        deleteTaskRequestDTO: DeleteTaskRequestDTO,
        getTasksRequestDTO: GetTasksRequestDTO
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiCalender.deleteTaskInApi(deleteTaskRequestDTO)
            if (response.status == "Success") {
                Log.d("murali", "Task Deleted Successfully")
                //after successfully deleting we are again making api call
                getAllTasksFromApiAndAddToRoomDataBase(getTasksRequestDTO)
            }
            Log.d("murali", "Task Failed To Delete")
        }
    }

}