package com.murali.taskmanager.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.murali.taskmanager.data.local.CalenderTaskModel
import com.murali.taskmanager.data.response.delete.DeleteTaskRequestDTO
import com.murali.taskmanager.data.response.get.GetTasksRequestDTO
import com.murali.taskmanager.data.response.post.PostTasksRequestDTO
import com.murali.taskmanager.repository.TaskRepository

class TaskViewModel(val repository: TaskRepository) : ViewModel() {

    //adding tasks to api
    fun addTaskApiToThroughRepository(calenderTaskModel: PostTasksRequestDTO) {
        repository.addTaskApiToDao(calenderTaskModel)
    }

    //getting all tasks from api but adding to room db
    fun getAllApiTasksFromRepository(getTasksRequestDTO: GetTasksRequestDTO) {
        repository.getAllTasksFromApiAndAddToRoomDataBase(getTasksRequestDTO)
    }

    //getting all tasks from room db using livedata
    fun getAllTasksFromRepository(): LiveData<List<CalenderTaskModel>> {
        return repository.getAllTasksFromDao()
    }

    fun getAllTasksFromRepositoryAccordingToDate(date: String): LiveData<List<CalenderTaskModel>> {
        return repository.getAllTasksFromDaoAccordingToDate(date)
    }

    //deleting tasks in api
    fun deleteTaskInRepository(
        deleteTaskRequestDTO: DeleteTaskRequestDTO,
        getTasksRequestDTO: GetTasksRequestDTO
    ) {
        repository.deleteTaskInApi(deleteTaskRequestDTO, getTasksRequestDTO)
    }

}