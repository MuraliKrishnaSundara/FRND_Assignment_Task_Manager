package com.murali.taskmanager.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.murali.taskmanager.api.CalendarTasksNetwork
import com.murali.taskmanager.api.CalendarTasksAPI
import com.murali.taskmanager.data.local.CalendarTaskDao
import com.murali.taskmanager.data.response.get.CalendarTaskModel
import com.murali.taskmanager.data.response.delete.DeleteTaskRequestDTO
import com.murali.taskmanager.data.response.get.GetTasksRequestDTO
import com.murali.taskmanager.data.response.get.GetTasksResponseDTO
import com.murali.taskmanager.data.response.post.PostTasksRequestDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CalendarTaskRepository @Inject constructor(val calendarTaskDao: CalendarTaskDao) {

    private val apiCalendar: CalendarTasksAPI =
        CalendarTasksNetwork.retrofit.create(CalendarTasksAPI::class.java)

    //adding task to api
    fun postTaskToApiThroughRepository(calenderTaskModel: PostTasksRequestDTO) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiCalendar.addTaskToAPI(calenderTaskModel)
            if (response.status == "Success") {
                Log.d("murali", "successfully task added")
            } else {
                Log.d("murali", "error task not added ${response.status}")
            }
        }
    }

    //getting tasks from api and adding to room database
    fun getAllTasksFromApiThroughRepository(getTasksRequestDTO: GetTasksRequestDTO) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiCalendar.getTasksFromAPI(getTasksRequestDTO)
            if (!response.tasks.isEmpty()) {

                storeTaskInRoomDataBaseThroughRepository(response)
                Log.d("murali", "Response getting success")

            }
            Log.d("murali", "Response getting error")
        }
    }

    //adding tasks to room database which are coming from api
    private fun storeTaskInRoomDataBaseThroughRepository(response: GetTasksResponseDTO) {
        val listOfTasks = ArrayList<CalendarTaskModel>()
        response.tasks.forEach {
            val newTask = CalendarTaskModel(
                it.task_id,
                it.task_detail.name,
                it.task_detail.description,
                it.task_detail.date
            )
            listOfTasks.add(newTask)
        }
        //deleting all tasks in room database
        calendarTaskDao.deleteAllTasksFromRoomDataBase()
        //after deleting all tasks in room database adding fresh tasks list from api
        calendarTaskDao.addTasksListFromApiToRoomDataBase(listOfTasks)
    }

    //getting all tasks from room database using livedata
    fun getAllTasksFromRoomDatabaseThroughRepository(): LiveData<List<CalendarTaskModel>> {
        return calendarTaskDao.getAllTasksFromRoomDataBase()
    }

    //getting all tasks from room database according to date selected using livedata
    fun getAllTasksFromRoomDatabaseAccordingToDateThroughRepository(date: String): LiveData<List<CalendarTaskModel>> {
        return calendarTaskDao.getAllTasksFromRoomDataBaseAccordingToDate(date)
    }

    //delete task in api
    fun deleteTaskInApiThroughRepository(
        deleteTaskRequestDTO: DeleteTaskRequestDTO,
        getTasksRequestDTO: GetTasksRequestDTO
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiCalendar.deleteTaskInApi(deleteTaskRequestDTO)
            if (response.status == "Success") {

                Log.d("murali", "Task Deleted Successfully")

                //after successfully deleting we are again making api call
                getAllTasksFromApiThroughRepository(getTasksRequestDTO)

            }
            Log.d("murali", "Task Failed To Delete")
        }
    }

}