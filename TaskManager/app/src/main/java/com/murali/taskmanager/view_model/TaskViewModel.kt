package com.murali.taskmanager.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.murali.taskmanager.data.local.CalenderTaskModel
import com.murali.taskmanager.repository.TaskRepository

class TaskViewModel(val repository: TaskRepository) : ViewModel() {

    fun addTaskToRepository(calenderTaskModel: CalenderTaskModel) {
        repository.addTaskToDao(calenderTaskModel)
    }

    fun getAllApiTasksFromRepository() {
        repository.getRemoteTasks()
    }

    fun getAllTasksFromRepository(): LiveData<List<CalenderTaskModel>> {
        return repository.getAllTasksFromDao()
    }

    fun getAllTasksFromRepositoryAccordingToDate(date: String): LiveData<List<CalenderTaskModel>> {
        return repository.getAllTasksFromDaoAccordingToDate(date)
    }

    fun deleteTaskInRepository(calenderTaskModel: CalenderTaskModel) {
        repository.deleteTaskInDao(calenderTaskModel)
    }

}