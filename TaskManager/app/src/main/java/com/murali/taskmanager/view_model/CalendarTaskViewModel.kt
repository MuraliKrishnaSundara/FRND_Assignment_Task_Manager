package com.murali.taskmanager.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.murali.taskmanager.data.response.get.CalendarTaskModel
import com.murali.taskmanager.data.response.delete.DeleteTaskRequestDTO
import com.murali.taskmanager.data.response.get.GetTasksRequestDTO
import com.murali.taskmanager.data.response.post.PostTasksRequestDTO
import com.murali.taskmanager.repository.CalendarTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarTaskViewModel @Inject constructor(val repositoryCalendar: CalendarTaskRepository) : ViewModel() {

    //adding tasks to api
    fun postTaskToApiThroughViewModel(calenderTaskModel: PostTasksRequestDTO) {
        repositoryCalendar.postTaskToApiThroughRepository(calenderTaskModel)
    }

    //getting all tasks from api but adding to room db
    fun getAllTasksFromApiThroughViewModel(getTasksRequestDTO: GetTasksRequestDTO) {
        repositoryCalendar.getAllTasksFromApiThroughRepository(getTasksRequestDTO)
    }

    //getting all tasks from room db using livedata
    fun getAllTasksFromRoomDatabaseThroughViewModel(): LiveData<List<CalendarTaskModel>> {
        return repositoryCalendar.getAllTasksFromRoomDatabaseThroughRepository()
    }

    //getting all tasks from room db according to date selected using livedata
    fun getAllTasksFromRoomDatabaseAccordingToDateThroughViewModel(date: String): LiveData<List<CalendarTaskModel>> {
        return repositoryCalendar.getAllTasksFromRoomDatabaseAccordingToDateThroughRepository(date)
    }

    //deleting task in api
    fun deleteTaskInApiThroughViewModel(
        deleteTaskRequestDTO: DeleteTaskRequestDTO,
        getTasksRequestDTO: GetTasksRequestDTO
    ) {
        repositoryCalendar.deleteTaskInApiThroughRepository(deleteTaskRequestDTO, getTasksRequestDTO)
    }

}