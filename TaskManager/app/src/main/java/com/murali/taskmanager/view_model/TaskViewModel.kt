package com.murali.taskmanager.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.murali.taskmanager.data.local.CalenderTaskModel
import com.murali.taskmanager.repository.TaskRepository

class TaskViewModel(val repo: TaskRepository) : ViewModel() {

    fun addTaskToRepository(calenderTaskModel: CalenderTaskModel) {
        repo.addTaskToDao(calenderTaskModel)
    }

    fun getAllTasksFromRepository(): LiveData<List<CalenderTaskModel>> {
        return repo.getAllTasksFromDao()
    }

    fun getAllTasksFromRepositoryAccordingToDate(date: String): LiveData<List<CalenderTaskModel>> {
        return repo.getAllTasksFromDaoAccordingToDate(date)
    }

    fun deleteTaskInRepository(calenderTaskModel: CalenderTaskModel) {
        repo.deleteTaskInDao(calenderTaskModel)
    }

}